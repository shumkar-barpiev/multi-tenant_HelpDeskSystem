package com.shumkar.helpdesksystem.common.config;

import com.shumkar.helpdesksystem.common.security.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

	@Bean
	public SecretKey jwtSecretKey(JwtProperties properties) {
		if (properties.getIssuer() == null || properties.getIssuer().isBlank()) {
			throw new IllegalStateException("security.jwt.issuer must not be blank");
		}
		if (properties.getAccessTokenTtl() == null
				|| properties.getAccessTokenTtl().isZero()
				|| properties.getAccessTokenTtl().isNegative()) {
			throw new IllegalStateException(
					"security.jwt.access-token-ttl must be greater than zero"
			);
		}
		if (properties.getRefreshTokenTtl() == null
				|| properties.getRefreshTokenTtl().isZero()
				|| properties.getRefreshTokenTtl().isNegative()) {
			throw new IllegalStateException(
					"security.jwt.refresh-token-ttl must be greater than zero"
			);
		}

		String configuredSecret = properties.getSecret();
		if (configuredSecret == null || configuredSecret.isBlank()) {
			throw new IllegalStateException(
					"security.jwt.secret must contain a Base64-encoded 256-bit secret"
			);
		}

		final byte[] keyBytes;
		try {
			keyBytes = Base64.getDecoder().decode(configuredSecret);
		} catch (IllegalArgumentException exception) {
			throw new IllegalStateException(
					"security.jwt.secret must be valid Base64",
					exception
			);
		}

		if (keyBytes.length < 32) {
			throw new IllegalStateException(
					"security.jwt.secret must decode to at least 32 bytes"
			);
		}

		return new SecretKeySpec(keyBytes, "HmacSHA256");
	}

	@Bean
	public JwtEncoder jwtEncoder(SecretKey secretKey) {
		return NimbusJwtEncoder.withSecretKey(secretKey)
				.algorithm(MacAlgorithm.HS256)
				.build();
	}

	@Bean
	public JwtDecoder jwtDecoder(
			SecretKey secretKey,
			JwtProperties properties
	) {
		NimbusJwtDecoder decoder = NimbusJwtDecoder
				.withSecretKey(secretKey)
				.macAlgorithm(MacAlgorithm.HS256)
				.build();

		decoder.setJwtValidator(
				JwtValidators.createDefaultWithIssuer(properties.getIssuer())
		);

		return decoder;
	}
}
