package com.shumkar.helpdesksystem.ticket.dto;

import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import com.shumkar.helpdesksystem.ticket.entity.type.TicketStatus;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.Ticket}
 */
@Value
public class TicketUpdateRequest implements Serializable {
	UUID categoryId;
	UUID slaPolicyId;

	@Size(max = 200)
	String subject;

	String description;
	TicketStatus status;
	UUID assignedToId;
	TicketPriority priority;
	Instant firstResponseDueAt;
	Instant resolutionDueAt;
	Instant firstRespondedAt;
	Instant resolvedAt;
	Instant closedAt;
}
