package com.shumkar.helpdesksystem.auth.dto;

import com.shumkar.helpdesksystem.organization.entity.type.UserRole;

import java.util.UUID;

public record OrganizationOptionResponse(
			UUID organizationId,
			String name,
			String slug,
			UserRole role
) {
}
