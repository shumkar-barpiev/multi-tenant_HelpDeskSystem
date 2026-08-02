package com.shumkar.helpdesksystem.organization.mapper;

import com.shumkar.helpdesksystem.organization.dto.OrganizationMembershipCreateRequest;
import com.shumkar.helpdesksystem.organization.dto.OrganizationMembershipResponse;
import com.shumkar.helpdesksystem.organization.dto.OrganizationMembershipUpdateRequest;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMembershipMapper {

	public OrganizationMembershipResponse toResponse(OrganizationMembership entity) {
		if (entity == null) {
			return null;
		}

		return new OrganizationMembershipResponse(
				entity.getId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.isActive(),
				entity.getVersion(),
				entity.getOrganization() != null ? entity.getOrganization().getId() : null,
				entity.getUser() != null ? entity.getUser().getId() : null,
				entity.getRole(),
				entity.getStatus(),
				entity.getJoinedAt()
		);
	}

	public OrganizationMembership toEntity(OrganizationMembershipCreateRequest request, Organization organization, User user) {
		if (request == null) {
			return null;
		}

		OrganizationMembership entity = new OrganizationMembership();
		entity.setOrganization(organization);
		entity.setUser(user);
		entity.setRole(request.getRole());
		entity.setStatus(request.getStatus());
		return entity;
	}

	public void updateEntity(OrganizationMembershipUpdateRequest request, OrganizationMembership entity) {
		if (request == null || entity == null) {
			return;
		}

		if (request.getRole() != null) {
			entity.setRole(request.getRole());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}
	}
}
