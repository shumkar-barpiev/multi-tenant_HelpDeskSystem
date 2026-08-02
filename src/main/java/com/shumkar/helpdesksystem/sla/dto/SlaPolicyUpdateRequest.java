package com.shumkar.helpdesksystem.sla.dto;

import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.shumkar.helpdesksystem.sla.entity.SlaPolicy}
 */
@Value
public class SlaPolicyUpdateRequest implements Serializable {
	@NotBlank(message = "SLA policy name can't be blank.")
	@Size(max = 100)
	String name;

	@NotNull(message = "Ticket priority can't be null.")
	TicketPriority priority;

	@NotNull(message = "First response minutes can't be null.")
	@Min(value = 1, message = "First response minutes must be at least 1.")
	Integer firstResponseMinutes;

	@NotNull(message = "Resolution minutes can't be null.")
	@Min(value = 1, message = "Resolution minutes must be at least 1.")
	Integer resolutionMinutes;
}
