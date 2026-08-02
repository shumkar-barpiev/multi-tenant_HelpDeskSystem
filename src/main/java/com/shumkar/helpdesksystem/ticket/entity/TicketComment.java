package com.shumkar.helpdesksystem.ticket.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
		name = "ticket_comments",
		indexes = {
				@Index(
						name = "idx_ticket_comments_ticket_id",
						columnList = "ticket_id"
				),
				@Index(
						name = "idx_ticket_comments_author_membership_id",
						columnList = "author_membership_id"
				),
				@Index(
						name = "idx_ticket_comments_org_ticket_created",
						columnList = "organization_id, ticket_id, created_at"
				)
		}
)
public class TicketComment extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "author_membership_id", nullable = false)
	private OrganizationMembership authorMembership;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String body;

	@Column(name = "edited_at")
	private Instant editedAt;

	@Column(name = "is_internal", nullable = false)
	private boolean internal = false;

	public void editBody(String newBody) {
		this.body = newBody;
		this.editedAt = Instant.now();
	}
}