package com.shumkar.helpdesksystem.ticket.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.sla.entity.SlaPolicy;
import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import com.shumkar.helpdesksystem.ticket.entity.type.TicketStatus;
import com.shumkar.helpdesksystem.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
		name = "tickets",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "uk_ticket_organization_number",
						columnNames = {"organization_id", "ticket_number"}
				)
		},
		indexes = {
				@Index(
						name = "idx_ticket_organization_status",
						columnList = "organization_id, status"
				),
				@Index(
						name = "idx_ticket_organization_category",
						columnList = "organization_id, category_id"
				),
				@Index(
						name = "idx_ticket_created_by",
						columnList = "created_by_id"
				),
				@Index(
						name = "idx_ticket_resolution_due_at",
						columnList = "resolution_due_at"
				)
		}
)
public class Ticket extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private TicketCategory category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sla_policy_id")
	private SlaPolicy slaPolicy;

	@Column(nullable = false, length = 200)
	private String subject;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(name = "ticket_number", nullable = false, length = 50)
	private String ticketNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TicketStatus status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by_id", nullable = false)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_to_id")
	private User assignedTo;

	@Column(name = "first_response_due_at")
	private Instant firstResponseDueAt;

	@Column(name = "resolution_due_at")
	private Instant resolutionDueAt;

	@Column(name = "first_responded_at")
	private Instant firstRespondedAt;

	@Column(name = "resolved_at")
	private Instant resolvedAt;

	@Column(name = "closed_at")
	private Instant closedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TicketPriority priority;
}