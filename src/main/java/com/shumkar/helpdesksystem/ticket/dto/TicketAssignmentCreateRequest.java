package com.shumkar.helpdesksystem.ticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketAssignment}
 */
@Value
public class TicketAssignmentCreateRequest implements Serializable {
	@NotNull(message = "Organization ID can't be null.")
	UUID organizationId;

	@NotNull(message = "Ticket ID can't be null.")
	UUID ticketId;

	@NotNull(message = "Agent membership ID can't be null.")
	UUID agentMembershipId;

	@NotNull(message = "Assigned-by membership ID can't be null.")
	UUID assignedByMembershipId;
}
