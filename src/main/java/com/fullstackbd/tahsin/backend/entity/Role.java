package com.fullstackbd.tahsin.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name", unique = true)
	private String name;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_services", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "services_id"))
	private List<Services> services;
	
	public void addServices(Services service) {
		if (this.services == null) {
			this.services = new ArrayList<>();
		}
		services.add(service);
	}
}