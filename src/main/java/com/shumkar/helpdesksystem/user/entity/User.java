package com.shumkar.helpdesksystem.user.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(
		name = "users",
		indexes = {
				@Index(
						name = "idx_users_username",
						columnList = "username"
				),
				@Index(
						name = "idx_users_last_login_at",
						columnList = "last_login_at"
				)
		}
)
public class User extends BaseEntity {
	@Column(nullable = false, length = 255)
	private String username;

	@Column(nullable = false, unique = true, length = 255)
	private String email;

	@Column(name = "password_hash", nullable = false, length = 255)
	private String password_hash;

	@Column(name = "email_verified", nullable = false)
	private boolean email_verified = false;

	@Column(name = "last_login_at")
	private Instant lastLoginAt;

	@OneToMany(mappedBy = "user")
	private Set<OrganizationMembership> memberships;
}
