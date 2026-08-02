package com.shumkar.helpdesksystem.organization.dto;

import com.shumkar.helpdesksystem.organization.entity.type.MembershipStatus;
import com.shumkar.helpdesksystem.organization.entity.type.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.shumkar.helpdesksystem.organization.entity.OrganizationMembership}
 */
@Value
public class OrganizationMembershipUpdateRequest implements Serializable {
	@NotNull(message = "User role can't be null.")
	UserRole role;

	@NotNull(message = "Membership status can't be null.")
	MembershipStatus status;
}
