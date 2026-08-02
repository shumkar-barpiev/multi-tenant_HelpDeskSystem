package com.shumkar.helpdesksystem.notification.dto;

import com.shumkar.helpdesksystem.notification.entity.type.NotificationType;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.shumkar.helpdesksystem.notification.entity.Notification}
 */
@Value
public class NotificationResponse implements Serializable {
	UUID id;
	Instant createdAt;
	Instant updatedAt;
	boolean active;
	Long version;
	UUID organizationId;
	UUID receiverMembershipId;
	UUID ticketId;
	NotificationType type;
	String title;
	String message;
	boolean isRead;
	Instant readAt;
}
