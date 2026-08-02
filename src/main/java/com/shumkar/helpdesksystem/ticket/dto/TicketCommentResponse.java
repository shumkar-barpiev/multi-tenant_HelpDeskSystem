package com.shumkar.helpdesksystem.ticket.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketComment}
 */
@Value
public class TicketCommentResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	UUID organizationId;
	UUID ticketId;
	UUID authorMembershipId;
	String body;
	Instant editedAt;
	boolean internal;
}
