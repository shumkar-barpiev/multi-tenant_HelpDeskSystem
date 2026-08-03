package com.shumkar.helpdesksystem.common.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserProvider {

	public AuthenticatedUser getCurrentUser() {
		Authentication authentication =
				SecurityContextHolder
						.getContext()
						.getAuthentication();

		if (authentication == null
				|| !(authentication.getPrincipal()
				instanceof AuthenticatedUser user)) {
			throw new AccessDeniedException(
					"Authenticated user is unavailable"
			);
		}

		return user;
	}

	public UUID getCurrentUserId() {
		return getCurrentUser().userId();
	}
}