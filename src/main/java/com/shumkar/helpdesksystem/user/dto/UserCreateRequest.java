package com.shumkar.helpdesksystem.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.shumkar.helpdesksystem.user.entity.User}
 */
@Value
public class UserCreateRequest implements Serializable {
	@NotBlank(message = "Username can't be blank.")
	@Size(max = 255)
	String username;

	@NotBlank(message = "Email can't be blank.")
	@Email(message = "Email should be valid.")
	@Size(max = 255)
	String email;

	@NotBlank(message = "Password can't be blank.")
	@Size(max = 255)
	String password;
}
