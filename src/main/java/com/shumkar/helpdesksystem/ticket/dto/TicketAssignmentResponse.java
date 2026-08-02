package com.shumkar.helpdesksystem.ticket.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketAssignment}
 */
@Value
public class TicketAssignmentResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	UUID organizationId;
	UUID ticketId;
	UUID agentMembershipId;
	UUID assignedByMembershipId;
	Instant assignedAt;
	Instant unassignedAt;
}
