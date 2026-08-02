package com.shumkar.helpdesksystem.ticket.mapper;

import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.ticket.dto.TicketActivityCreateRequest;
import com.shumkar.helpdesksystem.ticket.dto.TicketActivityResponse;
import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import com.shumkar.helpdesksystem.ticket.entity.TicketActivity;
import org.springframework.stereotype.Component;

@Component
public class TicketActivityMapper {

	public TicketActivityResponse toResponse(TicketActivity entity) {
		if (entity == null) {
			return null;
		}

		return new TicketActivityResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getTicket() != null ? entity.getTicket().getId() : null,
				entity.getActorMembership() != null ? entity.getActorMembership().getId() : null,
				entity.getActivityType(),
				entity.getOldValue(),
				entity.getNewValue(),
				entity.getMetadata()
		);
	}

	public TicketActivity toEntity(
			TicketActivityCreateRequest request,
			Organization organization,
			Ticket ticket,
			OrganizationMembership actorMembership
	) {
		if (request == null) {
			return null;
		}

		TicketActivity entity = new TicketActivity();
		entity.setOrganization(organization);
		entity.setTicket(ticket);
		entity.setActorMembership(actorMembership);
		entity.setActivityType(request.getActivityType());
		entity.setOldValue(request.getOldValue());
		entity.setNewValue(request.getNewValue());
		if (request.getMetadata() != null) {
			entity.setMetadata(request.getMetadata());
		}
		return entity;
	}
}
