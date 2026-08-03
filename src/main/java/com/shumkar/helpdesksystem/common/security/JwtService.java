package com.shumkar.helpdesksystem.common.security;

import java.util.UUID;

public interface JwtService {

	String generateAccessToken(AuthenticatedUser user);

	UUID extractUserId(String token);

	String extractEmail(String token);

	AuthenticatedUser extractAuthenticatedUser(String token);

	boolean isTokenValid(String token);
}
