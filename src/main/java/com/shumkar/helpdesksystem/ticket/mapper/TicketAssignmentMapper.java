package com.shumkar.helpdesksystem.ticket.mapper;

import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.ticket.dto.TicketAssignmentCreateRequest;
import com.shumkar.helpdesksystem.ticket.dto.TicketAssignmentResponse;
import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import com.shumkar.helpdesksystem.ticket.entity.TicketAssignment;
import org.springframework.stereotype.Component;

@Component
public class TicketAssignmentMapper {

	public TicketAssignmentResponse toResponse(TicketAssignment entity) {
		if (entity == null) {
			return null;
		}

		return new TicketAssignmentResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getTicket() != null ? entity.getTicket().getId() : null,
				entity.getAgentMembership() != null ? entity.getAgentMembership().getId() : null,
				entity.getAssignedByMembership() != null ? entity.getAssignedByMembership().getId() : null,
				entity.getAssignedAt(),
				entity.getUnassignedAt()
		);
	}

	public TicketAssignment toEntity(
			TicketAssignmentCreateRequest request,
			Organization organization,
			Ticket ticket,
			OrganizationMembership agentMembership,
			OrganizationMembership assignedByMembership
	) {
		if (request == null) {
			return null;
		}

		TicketAssignment entity = new TicketAssignment();
		entity.setOrganization(organization);
		entity.setTicket(ticket);
		entity.setAgentMembership(agentMembership);
		entity.setAssignedByMembership(assignedByMembership);
		return entity;
	}
}
