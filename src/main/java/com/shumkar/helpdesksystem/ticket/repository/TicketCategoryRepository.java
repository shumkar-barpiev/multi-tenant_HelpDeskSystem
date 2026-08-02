package com.shumkar.helpdesksystem.ticket.repository;

import com.shumkar.helpdesksystem.ticket.entity.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, UUID> {

	List<TicketCategory> findAllByOrganizationId(UUID organizationId);
}
