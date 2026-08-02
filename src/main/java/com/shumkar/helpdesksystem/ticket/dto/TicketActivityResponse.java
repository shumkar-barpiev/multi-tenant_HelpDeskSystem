package com.shumkar.helpdesksystem.ticket.dto;

import com.shumkar.helpdesksystem.ticket.entity.type.TicketActivityType;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketActivity}
 */
@Value
public class TicketActivityResponse implements Serializable {
	UUID id;
	Instant createdAt;
	UUID organizationId;
	UUID ticketId;
	UUID actorMembershipId;
	TicketActivityType activityType;
	String oldValue;
	String newValue;
	Map<String, Object> metadata;
}
