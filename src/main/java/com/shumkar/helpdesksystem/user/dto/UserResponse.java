package com.shumkar.helpdesksystem.user.dto;

import com.shumkar.helpdesksystem.organization.dto.OrganizationMembershipResponse;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.user.entity.User}
 */
@Value
public class UserResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	String username;
	String email;
	boolean emailVerified;
	Instant lastLoginAt;
	Set<OrganizationMembershipResponse> memberships;
}
