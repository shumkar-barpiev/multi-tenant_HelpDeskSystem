package com.shumkar.helpdesksystem.auth.dto;

import java.time.Instant;
import java.util.UUID;

public record RegistrationResponse(
			UUID userId,
			String username,
			String email,
			boolean emailVerified,
			Instant createdAt
) {
}
