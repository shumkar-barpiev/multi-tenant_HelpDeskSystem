package com.shumkar.helpdesksystem.auth.mapper;

import com.shumkar.helpdesksystem.auth.dto.OrganizationOptionResponse;
import com.shumkar.helpdesksystem.auth.dto.RegisterRequest;
import com.shumkar.helpdesksystem.auth.dto.RegistrationResponse;
import com.shumkar.helpdesksystem.auth.dto.TokenResponse;
import com.shumkar.helpdesksystem.common.security.AuthenticatedUser;
import com.shumkar.helpdesksystem.common.security.RoleAuthority;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.user.entity.User;
import com.shumkar.helpdesksystem.user.dto.UserCreateRequest;
import com.shumkar.helpdesksystem.user.dto.UserResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

	public OrganizationOptionResponse toOrganizationOption(
			OrganizationMembership membership
	) {
		Organization organization = membership.getOrganization();

		return new OrganizationOptionResponse(
				organization.getId(),
				organization.getName(),
				organization.getSlug(),
				membership.getRole()
		);
	}

	public AuthenticatedUser toAuthenticatedUser(
			User user,
			OrganizationMembership membership
	) {
		var authorities = RoleAuthority.getAuthorities(membership.getRole())
				.stream()
				.sorted()
				.map(SimpleGrantedAuthority::new)
				.toList();

		return new AuthenticatedUser(
				user.getId(),
				membership.getOrganization().getId(),
				membership.getId(),
				user.getEmail(),
				authorities
		);
	}

	public TokenResponse toTokenResponse(
			String accessToken,
			String refreshToken,
			long expiresInSeconds,
			OrganizationMembership membership
	) {
		return new TokenResponse(
				accessToken,
				refreshToken,
				"Bearer",
				expiresInSeconds,
				membership.getOrganization().getId()
		);
	}

	public UserCreateRequest toUserCreateRequest(RegisterRequest request) {
		return new UserCreateRequest(
				request.username().strip(),
				request.email().strip().toLowerCase(java.util.Locale.ROOT),
				request.password()
		);
	}

	public RegistrationResponse toRegistrationResponse(UserResponse user) {
		return new RegistrationResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.isEmailVerified(),
				user.getCreatedAt()
		);
	}
}
