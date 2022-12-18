package com.joshua.qrmenu.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;

	protected ProductEntity() {}

	public ProductEntity(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format(
				"Product[id=%d, name='%s']",
				id, name);
	}
}
