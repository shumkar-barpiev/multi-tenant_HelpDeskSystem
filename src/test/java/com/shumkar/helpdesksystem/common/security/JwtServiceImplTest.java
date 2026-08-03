package com.shumkar.helpdesksystem.common.security;

import com.shumkar.helpdesksystem.common.config.JwtConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceImplTest {

	@Test
	void generatesAndReadsTenantScopedAccessToken() {
		JwtService jwtService = createJwtService();
		UUID userId = UUID.randomUUID();
		UUID organizationId = UUID.randomUUID();
		UUID membershipId = UUID.randomUUID();
		AuthenticatedUser user = new AuthenticatedUser(
				userId,
				organizationId,
				membershipId,
				"agent@example.com",
				List.of(new SimpleGrantedAuthority("TICKET_READ"))
		);

		String token = jwtService.generateAccessToken(user);
		AuthenticatedUser extracted = jwtService.extractAuthenticatedUser(token);

		assertTrue(jwtService.isTokenValid(token));
		assertEquals(userId, jwtService.extractUserId(token));
		assertEquals("agent@example.com", jwtService.extractEmail(token));
		assertEquals(userId, extracted.userId());
		assertEquals(organizationId, extracted.organizationId());
		assertEquals(membershipId, extracted.membershipId());
		assertEquals("TICKET_READ", extracted.authorities().iterator().next().getAuthority());
	}

	@Test
	void rejectsTamperedToken() {
		JwtService jwtService = createJwtService();
		AuthenticatedUser user = new AuthenticatedUser(
				UUID.randomUUID(),
				UUID.randomUUID(),
				UUID.randomUUID(),
				"agent@example.com",
				List.of()
		);
		String token = jwtService.generateAccessToken(user);
		String tamperedToken = token.substring(0, token.length() - 1)
				+ (token.endsWith("a") ? "b" : "a");

		assertFalse(jwtService.isTokenValid(tamperedToken));
	}

	@Test
	void rejectsSecretsShorterThan256Bits() {
		JwtProperties properties = new JwtProperties();
		properties.setSecret(Base64.getEncoder().encodeToString(new byte[16]));

		assertThrows(
				IllegalStateException.class,
				() -> new JwtConfiguration().jwtSecretKey(properties)
		);
	}

	private JwtService createJwtService() {
		JwtProperties properties = new JwtProperties();
		properties.setIssuer("jwt-service-test");
		properties.setAccessTokenTtl(Duration.ofMinutes(5));
		properties.setSecret(Base64.getEncoder().encodeToString(
				"01234567890123456789012345678901"
						.getBytes(StandardCharsets.UTF_8)
		));

		JwtConfiguration configuration = new JwtConfiguration();
		SecretKey secretKey = configuration.jwtSecretKey(properties);

		return new JwtServiceImpl(
				configuration.jwtEncoder(secretKey),
				configuration.jwtDecoder(secretKey, properties),
				properties
		);
	}
}
