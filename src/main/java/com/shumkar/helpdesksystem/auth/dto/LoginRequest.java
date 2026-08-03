package com.shumkar.helpdesksystem.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LoginRequest(
		@NotBlank(message = "Email can't be blank.")
		@Email(message = "Email should be valid.")
		@Size(max = 255, message = "Email must not exceed 255 characters.")
		String email,

		@NotBlank(message = "Password can't be blank.")
		@Size(max = 255, message = "Password must not exceed 255 characters.")
		String password
) {
}
