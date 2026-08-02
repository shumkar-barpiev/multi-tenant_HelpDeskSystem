package com.shumkar.helpdesksystem.sla.dto;

import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.sla.entity.SlaPolicy}
 */
@Value
public class SlaPolicyResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	UUID organizationId;
	String name;
	TicketPriority priority;
	Integer firstResponseMinutes;
	Integer resolutionMinutes;
}
