package com.shumkar.helpdesksystem.ticket.repository;

import com.shumkar.helpdesksystem.ticket.entity.TicketAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment, UUID> {

	List<TicketAssignment> findAllByOrganizationIdAndTicketId(UUID organizationId, UUID ticketId);
}
