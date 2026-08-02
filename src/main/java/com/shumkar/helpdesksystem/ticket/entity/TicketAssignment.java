package com.shumkar.helpdesksystem.ticket.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
		name = "ticket_assignments",
		indexes = {
				@Index(
						name = "idx_ticket_assignment_org_ticket",
						columnList = "organization_id, ticket_id"
				),
				@Index(
						name = "idx_ticket_assignment_agent",
						columnList = "agent_membership_id"
				),
				@Index(
						name = "idx_ticket_assignment_active",
						columnList = "ticket_id, active"
				)
		}
)
public class TicketAssignment extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "agent_membership_id", nullable = false)
	private OrganizationMembership agentMembership;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "assigned_by_membership_id", nullable = false)
	private OrganizationMembership assignedByMembership;

	@Column(name = "assigned_at", nullable = false, updatable = false)
	private Instant assignedAt;

	@Column(name = "unassigned_at")
	private Instant unassignedAt;

	@Column(nullable = false)
	private boolean active = true;

	@PrePersist
	private void initializeAssignment() {
		if (assignedAt == null) {
			assignedAt = Instant.now();
		}
	}

	public void unassign() {
		this.active = false;
		this.unassignedAt = Instant.now();
	}
}