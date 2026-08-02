package com.shumkar.helpdesksystem.sla.repository;

import com.shumkar.helpdesksystem.sla.entity.SlaPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SlaPolicyRepository extends JpaRepository<SlaPolicy, UUID> {

	List<SlaPolicy> findAllByOrganizationId(UUID organizationId);
}
