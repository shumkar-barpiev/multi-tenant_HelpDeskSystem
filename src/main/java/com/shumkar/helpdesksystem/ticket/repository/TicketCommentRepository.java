package com.shumkar.helpdesksystem.ticket.repository;

import com.shumkar.helpdesksystem.ticket.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketCommentRepository extends JpaRepository<TicketComment, UUID> {
	List<TicketComment> findAllByTicketIdAndOrganizationIdAndInternalFalseOrderByCreatedAtAsc(Long ticketId, Long organizationId);
}
