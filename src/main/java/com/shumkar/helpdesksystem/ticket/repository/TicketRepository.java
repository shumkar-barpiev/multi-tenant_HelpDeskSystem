package com.shumkar.helpdesksystem.ticket.repository;

import com.shumkar.helpdesksystem.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

	Optional<Ticket> findByOrganizationIdAndTicketNumber(UUID organizationId, String ticketNumber);

	Page<Ticket> findAllByOrganizationId(UUID organizationId, Pageable pageable);
}
