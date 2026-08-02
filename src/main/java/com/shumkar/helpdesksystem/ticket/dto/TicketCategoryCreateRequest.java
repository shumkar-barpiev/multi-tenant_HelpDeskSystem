package com.shumkar.helpdesksystem.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketCategory}
 */
@Value
public class TicketCategoryCreateRequest implements Serializable {
	@NotNull(message = "Organization ID can't be null.")
	UUID organizationId;

	@NotBlank(message = "Category name can't be blank.")
	@Size(max = 255)
	String name;

	@Size(max = 500)
	String description;
}
