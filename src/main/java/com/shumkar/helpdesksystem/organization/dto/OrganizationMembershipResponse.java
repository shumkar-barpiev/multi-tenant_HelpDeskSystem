package com.shumkar.helpdesksystem.organization.dto;

import com.shumkar.helpdesksystem.organization.entity.type.MembershipStatus;
import com.shumkar.helpdesksystem.organization.entity.type.UserRole;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.organization.entity.OrganizationMembership}
 */
@Value
public class OrganizationMembershipResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	UUID organizationId;
	UUID userId;
	UserRole role;
	MembershipStatus status;
	Instant joinedAt;
}