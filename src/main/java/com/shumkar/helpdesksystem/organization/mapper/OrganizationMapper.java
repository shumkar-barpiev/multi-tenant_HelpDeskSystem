package com.shumkar.helpdesksystem.organization.mapper;

import com.shumkar.helpdesksystem.organization.dto.OrganizationCreateRequest;
import com.shumkar.helpdesksystem.organization.dto.OrganizationMembershipResponse;
import com.shumkar.helpdesksystem.organization.dto.OrganizationResponse;
import com.shumkar.helpdesksystem.organization.dto.OrganizationUpdateRequest;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationMapper {

	private final OrganizationMembershipMapper membershipMapper;

	public OrganizationResponse toResponse(Organization entity) {
		if (entity == null) {
			return null;
		}

		List<OrganizationMembershipResponse> memberships = entity.getMemberships() != null
				? entity.getMemberships().stream()
				.map(membershipMapper::toResponse)
				.toList()
				: Collections.emptyList();

		return new OrganizationResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getName(),
				entity.getSlug(),
				entity.getStatus(),
				memberships
		);
	}

	public Organization toEntity(OrganizationCreateRequest request) {
		if (request == null) {
			return null;
		}

		Organization entity = new Organization();
		entity.setName(request.getName());
		entity.setSlug(request.getSlug());
		entity.setStatus(request.getStatus());
		return entity;
	}

	public void updateEntity(OrganizationUpdateRequest request, Organization entity) {
		if (request == null || entity == null) {
			return;
		}

		if (request.getName() != null) {
			entity.setName(request.getName());
		}
		if (request.getSlug() != null) {
			entity.setSlug(request.getSlug());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}
	}
}
