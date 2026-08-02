package com.shumkar.helpdesksystem.ticket.mapper;

import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.sla.entity.SlaPolicy;
import com.shumkar.helpdesksystem.ticket.dto.TicketCreateRequest;
import com.shumkar.helpdesksystem.ticket.dto.TicketResponse;
import com.shumkar.helpdesksystem.ticket.dto.TicketUpdateRequest;
import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import com.shumkar.helpdesksystem.ticket.entity.TicketCategory;
import com.shumkar.helpdesksystem.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

	public TicketResponse toResponse(Ticket entity) {
		if (entity == null) {
			return null;
		}

		return new TicketResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getCategory() != null ? entity.getCategory().getId() : null,
				entity.getSlaPolicy() != null ? entity.getSlaPolicy().getId() : null,
				entity.getSubject(),
				entity.getDescription(),
				entity.getTicketNumber(),
				entity.getStatus(),
				entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null,
				entity.getAssignedTo() != null ? entity.getAssignedTo().getId() : null,
				entity.getFirstResponseDueAt(),
				entity.getResolutionDueAt(),
				entity.getFirstRespondedAt(),
				entity.getResolvedAt(),
				entity.getClosedAt(),
				entity.getPriority()
		);
	}

	public Ticket toEntity(
			TicketCreateRequest request,
			Organization organization,
			TicketCategory category,
			SlaPolicy slaPolicy,
			User createdBy,
			User assignedTo
	) {
		if (request == null) {
			return null;
		}

		Ticket entity = new Ticket();
		entity.setOrganization(organization);
		entity.setCategory(category);
		entity.setSlaPolicy(slaPolicy);
		entity.setSubject(request.getSubject());
		entity.setDescription(request.getDescription());
		entity.setTicketNumber(request.getTicketNumber());
		entity.setStatus(request.getStatus());
		entity.setCreatedBy(createdBy);
		entity.setAssignedTo(assignedTo);
		entity.setPriority(request.getPriority());
		entity.setFirstResponseDueAt(request.getFirstResponseDueAt());
		entity.setResolutionDueAt(request.getResolutionDueAt());
		return entity;
	}

	public void updateEntity(
			TicketUpdateRequest request,
			Ticket entity,
			TicketCategory category,
			SlaPolicy slaPolicy,
			User assignedTo
	) {
		if (request == null || entity == null) {
			return;
		}

		if (category != null) {
			entity.setCategory(category);
		}
		if (slaPolicy != null) {
			entity.setSlaPolicy(slaPolicy);
		}
		if (request.getSubject() != null) {
			entity.setSubject(request.getSubject());
		}
		if (request.getDescription() != null) {
			entity.setDescription(request.getDescription());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}
		if (assignedTo != null) {
			entity.setAssignedTo(assignedTo);
		}
		if (request.getPriority() != null) {
			entity.setPriority(request.getPriority());
		}
		if (request.getFirstResponseDueAt() != null) {
			entity.setFirstResponseDueAt(request.getFirstResponseDueAt());
		}
		if (request.getResolutionDueAt() != null) {
			entity.setResolutionDueAt(request.getResolutionDueAt());
		}
		if (request.getFirstRespondedAt() != null) {
			entity.setFirstRespondedAt(request.getFirstRespondedAt());
		}
		if (request.getResolvedAt() != null) {
			entity.setResolvedAt(request.getResolvedAt());
		}
		if (request.getClosedAt() != null) {
			entity.setClosedAt(request.getClosedAt());
		}
	}
}
