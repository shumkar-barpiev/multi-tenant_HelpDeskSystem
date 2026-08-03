package com.shumkar.helpdesksystem.auth.controller;

import com.shumkar.helpdesksystem.auth.dto.LoginRequest;
import com.shumkar.helpdesksystem.auth.dto.LogoutRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationLoginRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationOptionResponse;
import com.shumkar.helpdesksystem.auth.dto.RefreshTokenRequest;
import com.shumkar.helpdesksystem.auth.dto.RegisterRequest;
import com.shumkar.helpdesksystem.auth.dto.RegistrationResponse;
import com.shumkar.helpdesksystem.auth.dto.TokenResponse;
import com.shumkar.helpdesksystem.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/organizations")
	public ResponseEntity<List<OrganizationOptionResponse>> getOrganizations(
			@Valid @RequestBody LoginRequest request
	) {
		return ResponseEntity.ok(
				authService.getAvailableOrganizations(request)
		);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(
			@Valid @RequestBody OrganizationLoginRequest request
	) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refresh(
			@Valid @RequestBody RefreshTokenRequest request
	) {
		return ResponseEntity.ok(authService.refresh(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
			@Valid @RequestBody LogoutRequest request
	) {
		authService.logout(request);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> register(
			@Valid @RequestBody RegisterRequest request
	) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(authService.register(request));
	}
}
