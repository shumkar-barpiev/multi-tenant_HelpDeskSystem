package com.shumkar.helpdesksystem.organization.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.type.OrganizationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "organizations")
public class Organization extends BaseEntity {
	@Column(nullable = false, length = 200)
	private String name;

	@Column(nullable = false, unique = true, length = 100)
	private String slug;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private OrganizationStatus status;

	@OneToMany(
			mappedBy = "organization",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<OrganizationMembership> memberships = new ArrayList<>();
}
