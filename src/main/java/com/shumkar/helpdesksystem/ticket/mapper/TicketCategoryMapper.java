package com.shumkar.helpdesksystem.ticket.mapper;

import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.ticket.dto.TicketCategoryCreateRequest;
import com.shumkar.helpdesksystem.ticket.dto.TicketCategoryResponse;
import com.shumkar.helpdesksystem.ticket.dto.TicketCategoryUpdateRequest;
import com.shumkar.helpdesksystem.ticket.entity.TicketCategory;
import org.springframework.stereotype.Component;

@Component
public class TicketCategoryMapper {

	public TicketCategoryResponse toResponse(TicketCategory entity) {
		if (entity == null) {
			return null;
		}

		return new TicketCategoryResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getName(),
				entity.getDescription()
		);
	}

	public TicketCategory toEntity(TicketCategoryCreateRequest request, Organization organization) {
		if (request == null) {
			return null;
		}

		TicketCategory entity = new TicketCategory();
		entity.setOrganization(organization);
		entity.setName(request.getName());
		entity.setDescription(request.getDescription());
		return entity;
	}

	public void updateEntity(TicketCategoryUpdateRequest request, TicketCategory entity) {
		if (request == null || entity == null) {
			return;
		}

		if (request.getName() != null) {
			entity.setName(request.getName());
		}
		if (request.getDescription() != null) {
			entity.setDescription(request.getDescription());
		}
	}
}
