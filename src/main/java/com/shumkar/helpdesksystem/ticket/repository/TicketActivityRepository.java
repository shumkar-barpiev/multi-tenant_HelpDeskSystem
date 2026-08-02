package com.shumkar.helpdesksystem.ticket.repository;

import com.shumkar.helpdesksystem.ticket.entity.TicketActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketActivityRepository extends JpaRepository<TicketActivity, UUID> {

	List<TicketActivity> findAllByOrganizationIdAndTicketIdOrderByCreatedAtDesc(UUID organizationId, UUID ticketId);
}
