package com.shumkar.helpdesksystem.auth.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SwitchOrganizationRequest(
		@NotNull(message = "Organization ID is required.")
		UUID organizationId
) {
}
