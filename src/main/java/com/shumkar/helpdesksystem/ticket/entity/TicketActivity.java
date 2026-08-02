package com.shumkar.helpdesksystem.ticket.entity;

import com.shumkar.helpdesksystem.common.persistence.AuditableEntity;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.ticket.entity.type.TicketActivityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(
		name = "ticket_activities",
		indexes = {
				@Index(
						name = "idx_ticket_activities_org_ticket_created",
						columnList = "organization_id, ticket_id, created_at"
				),
				@Index(
						name = "idx_ticket_activities_actor",
						columnList = "actor_membership_id"
				),
				@Index(
						name = "idx_ticket_activities_type",
						columnList = "organization_id, activity_type"
				)
		}
)
public class TicketActivity extends AuditableEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false, updatable = false)
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ticket_id", nullable = false, updatable = false)
	private Ticket ticket;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_membership_id", updatable = false)
	private OrganizationMembership actorMembership;

	@Enumerated(EnumType.STRING)
	@Column(
			name = "activity_type",
			nullable = false,
			updatable = false,
			length = 50
	)
	private TicketActivityType activityType;

	@Column(
			name = "old_value",
			columnDefinition = "TEXT",
			updatable = false
	)
	private String oldValue;

	@Column(
			name = "new_value",
			columnDefinition = "TEXT",
			updatable = false
	)
	private String newValue;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(
			name = "metadata",
			columnDefinition = "jsonb",
			updatable = false
	)
	private Map<String, Object> metadata = new HashMap<>();
}