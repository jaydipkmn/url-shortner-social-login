package com.jaydip.urlshortner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "api_keys")
public class ApiKeys {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "api_key_id")
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "api_key")
	private String apiKey;

}
