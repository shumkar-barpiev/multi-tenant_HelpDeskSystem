package com.shumkar.helpdesksystem.organization.repository;

import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationMembershipRepository extends JpaRepository<OrganizationMembership, UUID> {

	Optional<OrganizationMembership> findByUserIdAndOrganizationId(UUID userId, UUID organizationId);

	List<OrganizationMembership> findAllByUserId(UUID userId);

	List<OrganizationMembership> findAllByOrganizationId(UUID organizationId);
}
