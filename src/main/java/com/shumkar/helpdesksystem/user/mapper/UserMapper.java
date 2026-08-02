package com.shumkar.helpdesksystem.user.mapper;

import com.shumkar.helpdesksystem.organization.dto.OrganizationMembershipResponse;
import com.shumkar.helpdesksystem.organization.mapper.OrganizationMembershipMapper;
import com.shumkar.helpdesksystem.user.dto.UserCreateRequest;
import com.shumkar.helpdesksystem.user.dto.UserResponse;
import com.shumkar.helpdesksystem.user.dto.UserUpdateRequest;
import com.shumkar.helpdesksystem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final OrganizationMembershipMapper membershipMapper;

	public UserResponse toResponse(User entity) {
		if (entity == null) {
			return null;
		}

		Set<OrganizationMembershipResponse> memberships = entity.getMemberships() != null
				? entity.getMemberships().stream()
				.map(membershipMapper::toResponse)
				.collect(Collectors.toSet())
				: Collections.emptySet();

		return new UserResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getUsername(),
				entity.getEmail(),
				entity.isEmailVerified(),
				entity.getLastLoginAt(),
				memberships
		);
	}

	public User toEntity(UserCreateRequest request) {
		if (request == null) {
			return null;
		}

		User entity = new User();
		entity.setUsername(request.getUsername());
		entity.setEmail(request.getEmail());
		entity.setPasswordHash(request.getPassword());
		return entity;
	}

	public void updateEntity(UserUpdateRequest request, User entity) {
		if (request == null || entity == null) {
			return;
		}

		if (request.getUsername() != null) {
			entity.setUsername(request.getUsername());
		}
		if (request.getEmail() != null) {
			entity.setEmail(request.getEmail());
		}
	}
}
