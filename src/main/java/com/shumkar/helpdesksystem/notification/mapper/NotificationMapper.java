package com.shumkar.helpdesksystem.notification.mapper;

import com.shumkar.helpdesksystem.notification.dto.NotificationResponse;
import com.shumkar.helpdesksystem.notification.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

	public NotificationResponse toResponse(Notification entity) {
		if (entity == null) {
			return null;
		}

		return new NotificationResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getReceiverMembership() != null ? entity.getReceiverMembership().getId() : null,
				entity.getTicket() != null ? entity.getTicket().getId() : null,
				entity.getType(),
				entity.getTitle(),
				entity.getMessage(),
				entity.isRead(),
				entity.getReadAt()
		);
	}
}
