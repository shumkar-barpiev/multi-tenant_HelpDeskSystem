package com.shumkar.helpdesksystem.ticket.dto;

import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import com.shumkar.helpdesksystem.ticket.entity.type.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.Ticket}
 */
@Value
public class TicketCreateRequest implements Serializable {
	@NotNull(message = "Organization ID can't be null.")
	UUID organizationId;

	@NotNull(message = "Category ID can't be null.")
	UUID categoryId;

	UUID slaPolicyId;

	@NotBlank(message = "Subject can't be blank.")
	@Size(max = 200)
	String subject;

	@NotBlank(message = "Description can't be blank.")
	String description;

	@NotBlank(message = "Ticket number can't be blank.")
	@Size(max = 50)
	String ticketNumber;

	@NotNull(message = "Status can't be null.")
	TicketStatus status;

	@NotNull(message = "Created-by user ID can't be null.")
	UUID createdById;

	UUID assignedToId;

	@NotNull(message = "Priority can't be null.")
	TicketPriority priority;

	Instant firstResponseDueAt;
	Instant resolutionDueAt;
}
