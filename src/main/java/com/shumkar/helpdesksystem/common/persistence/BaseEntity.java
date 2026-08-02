package com.shumkar.helpdesksystem.common.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends AuditableEntity {

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

	@Column(name = "is_active", nullable = false)
	private boolean active = true;

	@Version
	@Column(nullable = false)
	private Long version;
}
