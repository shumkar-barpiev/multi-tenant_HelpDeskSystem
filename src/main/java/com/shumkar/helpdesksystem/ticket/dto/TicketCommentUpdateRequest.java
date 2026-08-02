package com.shumkar.helpdesksystem.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.shumkar.helpdesksystem.ticket.entity.TicketComment}
 */
@Value
public class TicketCommentUpdateRequest implements Serializable {
	@NotBlank(message = "Comment body can't be blank.")
	String body;

	boolean internal;
}
