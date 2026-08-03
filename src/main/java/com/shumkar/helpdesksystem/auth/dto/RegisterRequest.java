package com.shumkar.helpdesksystem.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
			@NotBlank(message = "Username can't be blank.")
			@Size(min = 3, max = 255, message = "Username must contain between 3 and 255 characters.")
			String username,

			@NotBlank(message = "Email can't be blank.")
			@Email(message = "Email should be valid.")
			@Size(max = 255, message = "Email must not exceed 255 characters.")
			String email,

			@NotBlank(message = "Password can't be blank.")
			@Size(min = 8, max = 72, message = "Password must contain between 8 and 72 characters.")
			String password
) {
}
