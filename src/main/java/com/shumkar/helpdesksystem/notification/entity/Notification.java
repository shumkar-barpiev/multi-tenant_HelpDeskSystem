package com.shumkar.helpdesksystem.notification.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.notification.entity.type.NotificationType;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
		name = "notifications",
		indexes = {
				@Index(
						name = "idx_notifications_receiver_created",
						columnList = "receiver_membership_id, created_at"
				),
				@Index(
						name = "idx_notifications_receiver_read",
						columnList = "receiver_membership_id, read_at"
				),
				@Index(
						name = "idx_notifications_org_ticket",
						columnList = "organization_id, ticket_id"
				)
		}
)
public class Notification extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "receiver_membership_id", nullable = false)
	private OrganizationMembership receiverMembership;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;

	@Enumerated(EnumType.STRING)
	@Column(name = "notification_type", nullable = false, length = 50)
	private NotificationType type;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(name = "read_at")
	private Instant readAt;

	public boolean isRead() {
		return readAt != null;
	}

	public void markAsRead() {
		if (readAt == null) {
			readAt = Instant.now();
		}
	}
}