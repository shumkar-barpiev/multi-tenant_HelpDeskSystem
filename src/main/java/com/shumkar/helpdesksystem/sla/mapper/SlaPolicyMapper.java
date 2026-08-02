package com.shumkar.helpdesksystem.sla.mapper;

import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.sla.dto.SlaPolicyCreateRequest;
import com.shumkar.helpdesksystem.sla.dto.SlaPolicyResponse;
import com.shumkar.helpdesksystem.sla.dto.SlaPolicyUpdateRequest;
import com.shumkar.helpdesksystem.sla.entity.SlaPolicy;
import org.springframework.stereotype.Component;

@Component
public class SlaPolicyMapper {

	public SlaPolicyResponse toResponse(SlaPolicy entity) {
		if (entity == null) {
			return null;
		}

		return new SlaPolicyResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getName(),
				entity.getPriority(),
				entity.getFirstResponseMinutes(),
				entity.getResolutionMinutes()
		);
	}

	public SlaPolicy toEntity(SlaPolicyCreateRequest request, Organization organization) {
		if (request == null) {
			return null;
		}

		SlaPolicy entity = new SlaPolicy();
		entity.setOrganization(organization);
		entity.setName(request.getName());
		entity.setPriority(request.getPriority());
		entity.setFirstResponseMinutes(request.getFirstResponseMinutes());
		entity.setResolutionMinutes(request.getResolutionMinutes());
		return entity;
	}

	public void updateEntity(SlaPolicyUpdateRequest request, SlaPolicy entity) {
		if (request == null || entity == null) {
			return;
		}

		if (request.getName() != null) {
			entity.setName(request.getName());
		}
		if (request.getPriority() != null) {
			entity.setPriority(request.getPriority());
		}
		if (request.getFirstResponseMinutes() != null) {
			entity.setFirstResponseMinutes(request.getFirstResponseMinutes());
		}
		if (request.getResolutionMinutes() != null) {
			entity.setResolutionMinutes(request.getResolutionMinutes());
		}
	}
}
