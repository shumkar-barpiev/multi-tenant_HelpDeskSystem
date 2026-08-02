package com.shumkar.helpdesksystem.organization.dto;

import com.shumkar.helpdesksystem.organization.entity.type.OrganizationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.shumkar.helpdesksystem.organization.entity.Organization}
 */
@Value
public class OrganizationCreateRequest implements Serializable {
	@Size(max = 200)
	@NotBlank(message = "Organization name can't be blank.")
	String name;

	@Size(max = 100)
	@NotBlank(message = "Organization Slug can't be blank.")
	String slug;

	@NotNull
	OrganizationStatus status;
}