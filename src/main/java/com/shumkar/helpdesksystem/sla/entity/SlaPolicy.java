package com.shumkar.helpdesksystem.sla.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.sla.entity.type.TicketPriority;
import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(
		name = "sla_policies",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "uk_sla_policy_org_name",
						columnNames = {"organization_id", "name"}
				)
		}
)
public class SlaPolicy extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@OneToMany(mappedBy = "slaPolicy")
	private List<Ticket> tickets;

	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TicketPriority priority;

	@Column(name = "first_response_minutes", nullable = false)
	private Integer firstResponseMinutes;

	@Column(name = "resolution_minutes", nullable = false)
	private Integer resolutionMinutes;
}
