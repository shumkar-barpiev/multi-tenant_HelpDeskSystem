package com.shumkar.helpdesksystem.notification.repository;

import com.shumkar.helpdesksystem.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository
		extends JpaRepository<Notification, UUID> {

	Page<Notification>
	findAllByReceiverMembership_IdAndOrganization_IdOrderByCreatedAtDesc(
			UUID receiverMembershipId,
			UUID organizationId,
			Pageable pageable
	);

	Page<Notification>
	findAllByReceiverMembership_IdAndOrganization_IdAndReadAtIsNullOrderByCreatedAtDesc(
			UUID receiverMembershipId,
			UUID organizationId,
			Pageable pageable
	);

	long countByReceiverMembership_IdAndOrganization_IdAndReadAtIsNull(
			UUID receiverMembershipId,
			UUID organizationId
	);
}