package com.shumkar.helpdesksystem.organization.dto;

import com.shumkar.helpdesksystem.organization.entity.type.OrganizationStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.organization.entity.Organization}
 */
@Value
public class OrganizationResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	String name;
	String slug;
	OrganizationStatus status;
	List<OrganizationMembershipResponse> memberships;
}