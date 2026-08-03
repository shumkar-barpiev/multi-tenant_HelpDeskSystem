package com.shumkar.helpdesksystem.auth.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.OrganizationMembership;
import com.shumkar.helpdesksystem.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(
		name = "refresh_tokens",
		indexes = {
				@Index(name = "idx_refresh_token_user", columnList = "user_id"),
				@Index(name = "idx_refresh_token_expires_at", columnList = "expires_at")
		}
)
public class RefreshToken extends BaseEntity {

	@Column(name = "token_hash", nullable = false, unique = true, length = 64)
	private String tokenHash;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "membership_id", nullable = false)
	private OrganizationMembership membership;

	@Column(name = "expires_at", nullable = false)
	private Instant expiresAt;

	@Column(name = "revoked_at")
	private Instant revokedAt;

	@Column(name = "replaced_by_token_hash", length = 64)
	private String replacedByTokenHash;

	public boolean isUsableAt(Instant instant) {
		return isActive()
				&& revokedAt == null
				&& expiresAt.isAfter(instant);
	}
}
