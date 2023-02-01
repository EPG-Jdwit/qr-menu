package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
		name = "products",
		schema = "public"
)
public class ProductEntity {

	@Id
	@GeneratedValue(generator = "prod_generator")
	@GenericGenerator(
			name = "prod_generator",
			strategy = "com.joshua.qrmenu.repositories.util.IdGenerator"
	)
	@Column(
			name = "product_id",
			updatable = false
	)
	private Long productId;
	private String name;

	private Double price;

	private String description;

	public ProductEntity(String name, Double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	@JoinTable(
			name = "subcategory_product",
			inverseJoinColumns = @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id"),
			joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
			foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
			inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
	)
	private Set<SubcategoryEntity> subcategories;

	@Override
	public String toString() {
		return String.format(
				"Product[id=%d, name='%s']",
				productId, name);
	}
}
