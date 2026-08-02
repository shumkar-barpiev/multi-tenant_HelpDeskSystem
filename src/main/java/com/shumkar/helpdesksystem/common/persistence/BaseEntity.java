package com.shumkar.helpdesksystem.common.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_at", updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", updatable = false)
	private Instant updatedAt;

	@Column(name = "is_active", nullable = false)
	private boolean active = true;
}
