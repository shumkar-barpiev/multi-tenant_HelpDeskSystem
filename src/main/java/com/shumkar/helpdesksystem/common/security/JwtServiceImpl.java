package com.shumkar.helpdesksystem.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

	private static final String TOKEN_TYPE = "token_type";
	private static final String ACCESS_TOKEN = "access";
	private static final String EMAIL = "email";
	private static final String ORGANIZATION_ID = "organization_id";
	private static final String MEMBERSHIP_ID = "membership_id";
	private static final String AUTHORITIES = "authorities";

	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	private final JwtProperties properties;

	@Override
	public String generateAccessToken(AuthenticatedUser user) {
		Instant issuedAt = Instant.now();
		Instant expiresAt = issuedAt.plus(properties.getAccessTokenTtl());

		List<String> authorities = user.authorities().stream()
				.map(GrantedAuthority::getAuthority)
				.sorted()
				.toList();

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(properties.getIssuer())
				.issuedAt(issuedAt)
				.expiresAt(expiresAt)
				.subject(user.userId().toString())
				.id(UUID.randomUUID().toString())
				.claim(TOKEN_TYPE, ACCESS_TOKEN)
				.claim(EMAIL, user.email())
				.claim(ORGANIZATION_ID, user.organizationId().toString())
				.claim(MEMBERSHIP_ID, user.membershipId().toString())
				.claim(AUTHORITIES, authorities)
				.build();

		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)
				.type("JWT")
				.build();

		return jwtEncoder.encode(
				JwtEncoderParameters.from(header, claims)
		).getTokenValue();
	}

	@Override
	public UUID extractUserId(String token) {
		return UUID.fromString(decodeAccessToken(token).getSubject());
	}

	@Override
	public String extractEmail(String token) {
		return requiredStringClaim(decodeAccessToken(token), EMAIL);
	}

	@Override
	public AuthenticatedUser extractAuthenticatedUser(String token) {
		Jwt jwt = decodeAccessToken(token);

		List<SimpleGrantedAuthority> authorities = jwt
				.getClaimAsStringList(AUTHORITIES)
				.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();

		return new AuthenticatedUser(
				UUID.fromString(jwt.getSubject()),
				UUID.fromString(requiredStringClaim(jwt, ORGANIZATION_ID)),
				UUID.fromString(requiredStringClaim(jwt, MEMBERSHIP_ID)),
				requiredStringClaim(jwt, EMAIL),
				authorities
		);
	}

	@Override
	public boolean isTokenValid(String token) {
		try {
			extractAuthenticatedUser(token);
			return true;
		} catch (JwtException | IllegalArgumentException exception) {
			return false;
		}
	}

	private Jwt decodeAccessToken(String token) {
		if (token == null || token.isBlank()) {
			throw new JwtException("JWT must not be blank");
		}

		Jwt jwt = jwtDecoder.decode(token);
		if (!ACCESS_TOKEN.equals(jwt.getClaimAsString(TOKEN_TYPE))) {
			throw new JwtException("JWT is not an access token");
		}

		requiredStringClaim(jwt, EMAIL);
		requiredStringClaim(jwt, ORGANIZATION_ID);
		requiredStringClaim(jwt, MEMBERSHIP_ID);

		List<String> authorities = jwt.getClaimAsStringList(AUTHORITIES);
		if (authorities == null) {
			throw new JwtException("JWT authorities claim is missing");
		}

		if (jwt.getSubject() == null || jwt.getSubject().isBlank()) {
			throw new JwtException("JWT subject is missing");
		}

		return jwt;
	}

	private String requiredStringClaim(Jwt jwt, String claimName) {
		String value = jwt.getClaimAsString(claimName);
		if (value == null || value.isBlank()) {
			throw new JwtException("JWT claim is missing: " + claimName);
		}
		return value;
	}
}
