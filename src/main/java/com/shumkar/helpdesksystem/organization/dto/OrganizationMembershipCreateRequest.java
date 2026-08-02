package com.shumkar.helpdesksystem.organization.dto;

import com.shumkar.helpdesksystem.organization.entity.type.MembershipStatus;
import com.shumkar.helpdesksystem.organization.entity.type.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.organization.entity.OrganizationMembership}
 */
@Value
public class OrganizationMembershipCreateRequest implements Serializable {
	@NotNull(message = "Organization ID can't be null.")
	UUID organizationId;

	@NotNull(message = "User ID can't be null.")
	UUID userId;

	@NotNull(message = "User role can't be null.")
	UserRole role;

	@NotNull(message = "Membership status can't be null.")
	MembershipStatus status;
}
