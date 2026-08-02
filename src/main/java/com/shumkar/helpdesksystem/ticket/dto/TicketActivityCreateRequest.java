package com.shumkar.helpdesksystem.ticket.dto;

import com.shumkar.helpdesksystem.ticket.entity.type.TicketActivityType;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketActivity}
 */
@Value
public class TicketActivityCreateRequest implements Serializable {
	@NotNull(message = "Organization ID can't be null.")
	UUID organizationId;

	@NotNull(message = "Ticket ID can't be null.")
	UUID ticketId;

	UUID actorMembershipId;

	@NotNull(message = "Activity type can't be null.")
	TicketActivityType activityType;

	String oldValue;
	String newValue;
	Map<String, Object> metadata;
}
