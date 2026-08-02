package com.shumkar.helpdesksystem.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketComment}
 */
@Value
public class TicketCommentCreateRequest implements Serializable {
	@NotNull(message = "Organization ID can't be null.")
	UUID organizationId;

	@NotNull(message = "Ticket ID can't be null.")
	UUID ticketId;

	@NotNull(message = "Author membership ID can't be null.")
	UUID authorMembershipId;

	@NotBlank(message = "Comment body can't be blank.")
	String body;

	boolean internal;
}
