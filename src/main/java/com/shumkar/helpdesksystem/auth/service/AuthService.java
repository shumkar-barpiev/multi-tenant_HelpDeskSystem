package com.shumkar.helpdesksystem.auth.service;

import com.shumkar.helpdesksystem.auth.dto.LoginRequest;
import com.shumkar.helpdesksystem.auth.dto.LogoutRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationLoginRequest;
import com.shumkar.helpdesksystem.auth.dto.OrganizationOptionResponse;
import com.shumkar.helpdesksystem.auth.dto.RefreshTokenRequest;
import com.shumkar.helpdesksystem.auth.dto.RegisterRequest;
import com.shumkar.helpdesksystem.auth.dto.RegistrationResponse;
import com.shumkar.helpdesksystem.auth.dto.TokenResponse;

import java.util.List;

public interface AuthService {

	List<OrganizationOptionResponse> getAvailableOrganizations(LoginRequest request);

	TokenResponse login(OrganizationLoginRequest request);

	TokenResponse refresh(RefreshTokenRequest request);

	void logout(LogoutRequest request);

	RegistrationResponse register(RegisterRequest request);
}
