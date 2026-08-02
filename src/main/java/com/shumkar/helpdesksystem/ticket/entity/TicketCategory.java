package com.shumkar.helpdesksystem.ticket.entity;

import com.shumkar.helpdesksystem.common.persistence.BaseEntity;
import com.shumkar.helpdesksystem.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ticket_categories")
public class TicketCategory extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Organization organization;

	@OneToMany(mappedBy = "category")
	private List<Ticket> tickets;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 500)
	private String description;

}
