package com.fullstackbd.tahsin.backend.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name", nullable = false, unique = true)
	private String companyName;
	@Column(name = "address", nullable = false)
	private String companyAddress;
	@Column(name = "phone", nullable = false, unique = true)
	private String companyPhone;
	@Column(name = "email", nullable = false, unique = true)
	private String companyEmail;
	@Column(name = "website", nullable = false, unique = true)
	private String companyWebsite;

}
