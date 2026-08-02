package com.shumkar.helpdesksystem.notification.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Request DTO for marking notifications as read.
 */
@Value
public class MarkNotificationReadRequest implements Serializable {
	@NotEmpty(message = "Notification IDs list can't be empty.")
	List<UUID> notificationIds;
}
