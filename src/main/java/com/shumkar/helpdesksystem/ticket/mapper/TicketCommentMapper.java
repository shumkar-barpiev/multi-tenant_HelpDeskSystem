package com.shumkar.helpdesksystem.ticket.mapper;

import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.ticket.dto.TicketCommentCreateRequest;
import com.shumkar.helpdesksystem.ticket.dto.TicketCommentResponse;
import com.shumkar.helpdesksystem.ticket.dto.TicketCommentUpdateRequest;
import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import com.shumkar.helpdesksystem.ticket.entity.TicketComment;
import org.springframework.stereotype.Component;

@Component
public class TicketCommentMapper {

	public TicketCommentResponse toResponse(TicketComment entity) {
		if (entity == null) {
			return null;
		}

		return new TicketCommentResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getTicket() != null ? entity.getTicket().getId() : null,
				entity.getAuthorMembership() != null ? entity.getAuthorMembership().getId() : null,
				entity.getBody(),
				entity.getEditedAt(),
				entity.isInternal()
		);
	}

	public TicketComment toEntity(
			TicketCommentCreateRequest request,
			Organization organization,
			Ticket ticket,
			OrganizationMembership authorMembership
	) {
		if (request == null) {
			return null;
		}

		TicketComment entity = new TicketComment();
		entity.setOrganization(organization);
		entity.setTicket(ticket);
		entity.setAuthorMembership(authorMembership);
		entity.setBody(request.getBody());
		entity.setInternal(request.isInternal());
		return entity;
	}

	public void updateEntity(TicketCommentUpdateRequest request, TicketComment entity) {
		if (request == null || entity == null) {
			return;
		}

		if (request.getBody() != null) {
			entity.editBody(request.getBody());
		}
		entity.setInternal(request.isInternal());
	}
}
