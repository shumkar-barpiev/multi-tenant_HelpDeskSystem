package com.shumkar.helpdesksystem.auth.service.implement;

import com.shumkar.helpdesksystem.auth.dto.LoginRequest;
import com.shumkar.helpdesksystem.auth.dto.LogoutRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationLoginRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationOptionResponse;
import com.shumkar.helpdesksystem.auth.dto.RefreshTokenRequest;
import com.shumkar.helpdesksystem.auth.dto.RegisterRequest;
import com.shumkar.helpdesksystem.auth.dto.RegistrationResponse;
import com.shumkar.helpdesksystem.auth.dto.TokenResponse;
import com.shumkar.helpdesksystem.auth.exception.AuthenticationFailedException;
import com.shumkar.helpdesksystem.auth.exception.InvalidRefreshTokenException;
import com.shumkar.helpdesksystem.auth.exception.OrganizationAccessDeniedException;
import com.shumkar.helpdesksystem.auth.entity.RefreshToken;
import com.shumkar.helpdesksystem.auth.mapper.AuthMapper;
import com.shumkar.helpdesksystem.auth.repository.RefreshTokenRepository;
import com.shumkar.helpdesksystem.auth.service.AuthService;
import com.shumkar.helpdesksystem.common.security.JwtProperties;
import com.shumkar.helpdesksystem.common.security.JwtService;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.organization.entity.type.MembershipStatus;
import com.shumkar.helpdesksystem.organization.entity.type.OrganizationStatus;
import com.shumkar.helpdesksystem.organization.repository.OrganizationMembershipRepository;
import com.shumkar.helpdesksystem.user.entity.User;
import com.shumkar.helpdesksystem.user.repository.UserRepository;
import com.shumkar.helpdesksystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

	private static final String INVALID_CREDENTIALS = "Invalid email or password";
	private static final String INVALID_REFRESH_TOKEN = "Refresh token is invalid or expired";
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	private final UserRepository userRepository;
	private final UserService userService;
	private final OrganizationMembershipRepository membershipRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthMapper authMapper;
	private final JwtProperties jwtProperties;

	@Override
	public List<OrganizationOptionResponse> getAvailableOrganizations(
			LoginRequest request
	) {
		User user = authenticate(request.email(), request.password());

		return activeMemberships(user).stream()
				.map(authMapper::toOrganizationOption)
				.sorted(Comparator.comparing(OrganizationOptionResponse::name))
				.toList();
	}

	@Override
	@Transactional
	public TokenResponse login(OrganizationLoginRequest request) {
		User user = authenticate(request.email(), request.password());

		OrganizationMembership membership = membershipRepository
				.findByUserIdAndOrganizationId(user.getId(), request.organizationId())
				.filter(this::isActiveMembership)
				.orElseThrow(() -> new OrganizationAccessDeniedException(
						"No active membership exists for the selected organization"
				));

		var authenticatedUser = authMapper.toAuthenticatedUser(user, membership);
		String accessToken = jwtService.generateAccessToken(authenticatedUser);
		String refreshToken = issueRefreshToken(user, membership, Instant.now()).rawToken();

		user.setLastLoginAt(Instant.now());
		userRepository.save(user);

		return authMapper.toTokenResponse(
				accessToken,
				refreshToken,
				jwtProperties.getAccessTokenTtl().toSeconds(),
				membership
		);
	}

	@Override
	@Transactional
	public TokenResponse refresh(RefreshTokenRequest request) {
		Instant now = Instant.now();
		RefreshToken currentToken = refreshTokenRepository
				.findByTokenHash(hashToken(request.refreshToken()))
				.filter(token -> token.isUsableAt(now))
				.orElseThrow(() -> new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN));

		User user = currentToken.getUser();
		OrganizationMembership membership = currentToken.getMembership();
		if (!user.isActive() || !isActiveMembership(membership)) {
			revoke(currentToken, now, null);
			throw new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN);
		}

		IssuedRefreshToken replacement = issueRefreshToken(user, membership, now);
		revoke(currentToken, now, replacement.entity().getTokenHash());

		String accessToken = jwtService.generateAccessToken(
				authMapper.toAuthenticatedUser(user, membership)
		);

		return authMapper.toTokenResponse(
				accessToken,
				replacement.rawToken(),
				jwtProperties.getAccessTokenTtl().toSeconds(),
				membership
		);
	}

	@Override
	@Transactional
	public void logout(LogoutRequest request) {
		refreshTokenRepository.findByTokenHash(hashToken(request.refreshToken()))
				.ifPresent(token -> {
					if (token.getRevokedAt() == null) {
						revoke(token, Instant.now(), null);
					}
				});
	}

	@Override
	@Transactional
	public RegistrationResponse register(RegisterRequest request) {
		return authMapper.toRegistrationResponse(
				userService.createUser(authMapper.toUserCreateRequest(request))
		);
	}

	private User authenticate(String email, String password) {
		User user = userRepository.findByEmailIgnoreCase(email.strip())
				.orElseThrow(() -> new AuthenticationFailedException(INVALID_CREDENTIALS));

		if (!user.isActive()
				|| !passwordEncoder.matches(password, user.getPasswordHash())) {
			throw new AuthenticationFailedException(INVALID_CREDENTIALS);
		}

		return user;
	}

	private List<OrganizationMembership> activeMemberships(User user) {
		return membershipRepository.findAllByUserId(user.getId()).stream()
				.filter(this::isActiveMembership)
				.toList();
	}

	private boolean isActiveMembership(OrganizationMembership membership) {
		return membership.isActive()
				&& membership.getStatus() == MembershipStatus.ACTIVE
				&& membership.getOrganization().isActive()
				&& membership.getOrganization().getStatus() == OrganizationStatus.ACTIVE;
	}

	private IssuedRefreshToken issueRefreshToken(
			User user,
			OrganizationMembership membership,
			Instant issuedAt
	) {
		byte[] tokenBytes = new byte[32];
		SECURE_RANDOM.nextBytes(tokenBytes);
		String rawToken = Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(tokenBytes);

		RefreshToken entity = new RefreshToken();
		entity.setTokenHash(hashToken(rawToken));
		entity.setUser(user);
		entity.setMembership(membership);
		entity.setExpiresAt(issuedAt.plus(jwtProperties.getRefreshTokenTtl()));

		return new IssuedRefreshToken(
				rawToken,
				refreshTokenRepository.save(entity)
		);
	}

	private void revoke(
			RefreshToken token,
			Instant revokedAt,
			String replacementHash
	) {
		token.setRevokedAt(revokedAt);
		token.setReplacedByTokenHash(replacementHash);
		token.setActive(false);
		refreshTokenRepository.save(token);
	}

	private String hashToken(String rawToken) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA-256")
					.digest(rawToken.getBytes(java.nio.charset.StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException exception) {
			throw new IllegalStateException("SHA-256 is unavailable", exception);
		}
	}

	private record IssuedRefreshToken(
			String rawToken,
			RefreshToken entity
	) {
	}
}
