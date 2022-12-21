package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(
			name = "product_id",
			updatable = false
	)
	private Long productId;
	private String name;

	private double price;

	private String description;

	@ManyToMany(mappedBy = "products")
	private Set<CategoryEntity> categories;

	protected ProductEntity() {}

	public ProductEntity(String name, Double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format(
				"Product[id=%d, name='%s']",
				productId, name);
	}
}
