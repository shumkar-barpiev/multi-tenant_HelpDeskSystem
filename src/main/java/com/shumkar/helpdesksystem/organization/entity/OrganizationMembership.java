package com.shumkar.helpdesksystem.organization.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.type.MembershipStatus;
import com.shumkar.helpdesksystem.organization.entity.type.UserRole;
import com.shumkar.helpdesksystem.user.entity.User;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Entity
@Getter
@Setter
@Table(
		name = "organization_memberships",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "uk_organization_membership_user_organization",
						columnNames = {"user_id", "organization_id"}
				)
		},
		indexes = {
				@Index(
						name = "idx_membership_user",
						columnList = "user_id"
				),
				@Index(
						name = "idx_membership_organization",
						columnList = "organization_id"
				)
		}
)
public class OrganizationMembership extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "organization_id", nullable = false)
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private UserRole role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private MembershipStatus status = MembershipStatus.ACTIVE;

	@Column(name = "joined_at", nullable = false, updatable = false)
	private Instant joinedAt;

	@PrePersist
	private void initializeMembership() {
		if (joinedAt == null) {
			joinedAt = Instant.now();
		}
	}
}
