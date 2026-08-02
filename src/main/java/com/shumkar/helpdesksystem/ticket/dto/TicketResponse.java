package com.shumkar.helpdesksystem.ticket.dto;

import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import com.shumkar.helpdesksystem.ticket.entity.type.TicketStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.Ticket}
 */
@Value
public class TicketResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	UUID organizationId;
	UUID categoryId;
	UUID slaPolicyId;
	String subject;
	String description;
	String ticketNumber;
	TicketStatus status;
	UUID createdById;
	UUID assignedToId;
	Instant firstResponseDueAt;
	Instant resolutionDueAt;
	Instant firstRespondedAt;
	Instant resolvedAt;
	Instant closedAt;
	TicketPriority priority;
}
