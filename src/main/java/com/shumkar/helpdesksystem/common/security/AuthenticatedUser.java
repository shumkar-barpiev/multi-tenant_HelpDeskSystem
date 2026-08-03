package com.shumkar.helpdesksystem.common.security;

import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;
import java.util.UUID;

public record AuthenticatedUser(
		UUID userId,
		String email,
		Collection<? extends GrantedAuthority> authorities
) implements Principal {

	@Override
	public String getName() {
		return email;
	}
}