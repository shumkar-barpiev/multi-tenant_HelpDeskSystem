package com.shumkar.helpdesksystem.auth.service.implement;

import com.shumkar.helpdesksystem.auth.dto.LogoutRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationLoginRequest;
import com.shumkar.helpdesksystem.auth.dto.RefreshTokenRequest;
import com.shumkar.helpdesksystem.auth.dto.RegisterRequest;
import com.shumkar.helpdesksystem.auth.entity.RefreshToken;
import com.shumkar.helpdesksystem.auth.mapper.AuthMapper;
import com.shumkar.helpdesksystem.auth.repository.RefreshTokenRepository;
import com.shumkar.helpdesksystem.common.security.JwtProperties;
import com.shumkar.helpdesksystem.common.security.JwtService;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.organization.entity.type.MembershipStatus;
import com.shumkar.helpdesksystem.organization.entity.type.OrganizationStatus;
import com.shumkar.helpdesksystem.organization.entity.type.UserRole;
import com.shumkar.helpdesksystem.organization.repository.OrganizationMembershipRepository;
import com.shumkar.helpdesksystem.user.dto.UserCreateRequest;
import com.shumkar.helpdesksystem.user.dto.UserResponse;
import com.shumkar.helpdesksystem.user.entity.User;
import com.shumkar.helpdesksystem.user.repository.UserRepository;
import com.shumkar.helpdesksystem.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

	private UserRepository userRepository;
	private UserService userService;
	private OrganizationMembershipRepository membershipRepository;
	private RefreshTokenRepository refreshTokenRepository;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	private AuthServiceImpl authService;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		userService = mock(UserService.class);
		membershipRepository = mock(OrganizationMembershipRepository.class);
		refreshTokenRepository = mock(RefreshTokenRepository.class);
		passwordEncoder = mock(PasswordEncoder.class);
		jwtService = mock(JwtService.class);

		JwtProperties properties = new JwtProperties();
		properties.setAccessTokenTtl(Duration.ofMinutes(15));
		properties.setRefreshTokenTtl(Duration.ofDays(30));

		authService = new AuthServiceImpl(
				userRepository,
				userService,
				membershipRepository,
				refreshTokenRepository,
				passwordEncoder,
				jwtService,
				new AuthMapper(),
				properties
		);

		when(refreshTokenRepository.save(any(RefreshToken.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
	}

	@Test
	void loginIssuesAccessAndHashedRefreshTokens() {
		User user = activeUser();
		OrganizationMembership membership = activeMembership(user);
		when(userRepository.findByEmailIgnoreCase("user@example.com"))
				.thenReturn(Optional.of(user));
		when(passwordEncoder.matches("password123", user.getPasswordHash()))
				.thenReturn(true);
		when(membershipRepository.findByUserIdAndOrganizationId(
				user.getId(), membership.getOrganization().getId()
		)).thenReturn(Optional.of(membership));
		when(jwtService.generateAccessToken(any())).thenReturn("signed-access-token");

		var response = authService.login(new OrganizationLoginRequest(
				"user@example.com",
				"password123",
				membership.getOrganization().getId()
		));

		assertEquals("signed-access-token", response.accessToken());
		assertNotNull(response.refreshToken());
		assertFalse(response.refreshToken().isBlank());

		ArgumentCaptor<RefreshToken> tokenCaptor = ArgumentCaptor.forClass(RefreshToken.class);
		verify(refreshTokenRepository).save(tokenCaptor.capture());
		assertEquals(64, tokenCaptor.getValue().getTokenHash().length());
		assertNotEquals(response.refreshToken(), tokenCaptor.getValue().getTokenHash());
	}

	@Test
	void refreshRotatesAndRevokesPreviousToken() {
		User user = activeUser();
		OrganizationMembership membership = activeMembership(user);
		RefreshToken current = new RefreshToken();
		current.setTokenHash("old-hash");
		current.setUser(user);
		current.setMembership(membership);
		current.setExpiresAt(Instant.now().plus(Duration.ofDays(1)));

		when(refreshTokenRepository.findByTokenHash(anyString()))
				.thenReturn(Optional.of(current));
		when(jwtService.generateAccessToken(any())).thenReturn("new-access-token");

		var response = authService.refresh(new RefreshTokenRequest("old-refresh-token"));

		assertEquals("new-access-token", response.accessToken());
		assertNotNull(response.refreshToken());
		assertFalse(current.isActive());
		assertNotNull(current.getRevokedAt());
		assertNotNull(current.getReplacedByTokenHash());
	}

	@Test
	void logoutRevokesExistingRefreshToken() {
		RefreshToken token = new RefreshToken();
		token.setExpiresAt(Instant.now().plus(Duration.ofDays(1)));
		when(refreshTokenRepository.findByTokenHash(anyString()))
				.thenReturn(Optional.of(token));

		authService.logout(new LogoutRequest("refresh-token"));

		assertFalse(token.isActive());
		assertNotNull(token.getRevokedAt());
		verify(refreshTokenRepository).save(token);
	}

	@Test
	void registrationNormalizesInputAndReturnsSafeResponse() {
		UUID userId = UUID.randomUUID();
		Instant createdAt = Instant.now();
		when(userService.createUser(any())).thenReturn(new UserResponse(
				userId,
				createdAt,
				null,
				true,
				0L,
				"new-user",
				"new@example.com",
				false,
				null,
				Collections.emptySet()
		));

		var response = authService.register(new RegisterRequest(
				"  new-user  ",
				"  NEW@EXAMPLE.COM  ",
				"password123"
		));

		ArgumentCaptor<UserCreateRequest> requestCaptor =
				ArgumentCaptor.forClass(UserCreateRequest.class);
		verify(userService).createUser(requestCaptor.capture());
		assertEquals("new-user", requestCaptor.getValue().getUsername());
		assertEquals("new@example.com", requestCaptor.getValue().getEmail());
		assertEquals(userId, response.userId());
		assertEquals(createdAt, response.createdAt());
	}

	private User activeUser() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail("user@example.com");
		user.setPasswordHash("encoded-password");
		user.setActive(true);
		return user;
	}

	private OrganizationMembership activeMembership(User user) {
		Organization organization = new Organization();
		organization.setId(UUID.randomUUID());
		organization.setName("Example Organization");
		organization.setSlug("example");
		organization.setStatus(OrganizationStatus.ACTIVE);
		organization.setActive(true);

		OrganizationMembership membership = new OrganizationMembership();
		membership.setId(UUID.randomUUID());
		membership.setUser(user);
		membership.setOrganization(organization);
		membership.setRole(UserRole.AGENT);
		membership.setStatus(MembershipStatus.ACTIVE);
		membership.setActive(true);
		return membership;
	}
}
