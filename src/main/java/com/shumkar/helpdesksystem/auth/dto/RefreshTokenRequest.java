package com.shumkar.helpdesksystem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
		@NotBlank(message = "Refresh token can't be blank.")
		@Size(max = 4096, message = "Refresh token must not exceed 4096 characters.")
		String refreshToken
) {
}
