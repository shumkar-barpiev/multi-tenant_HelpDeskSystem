package com.shumkar.helpdesksystem.auth.dto;

import java.util.UUID;

public record TokenResponse(
		String accessToken,
		String refreshToken,
		String tokenType,
		long expiresInSeconds,
		UUID organizationId
) {
}
