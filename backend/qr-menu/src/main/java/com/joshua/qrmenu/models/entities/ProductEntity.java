package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(
			name = "product_id",
			updatable = false
	)
	private Long productId;
	private String name;

	private Double price;

	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	@JoinTable(
			name = "category_product",
			inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"),
			joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
			foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
			inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
	)
	private Set<CategoryEntity> categories;

	public ProductEntity(String name, Double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public void preAddCategoryEntity(CategoryEntity categoryEntity) {
		categories.add(categoryEntity);
	}
	public void addCategoryEntity(CategoryEntity categoryEntity) {
		preAddCategoryEntity(categoryEntity);
		categoryEntity.preAddProductEntity(this);
	}

	public void preRemoveCategoryEntity(CategoryEntity categoryEntity) {
		categories.remove(categoryEntity);
	}
	public void removeCategoryEntity(CategoryEntity categoryEntity) {
		preRemoveCategoryEntity(categoryEntity);
		categoryEntity.preRemoveProductEntity(this);
	}

	@Override
	public String toString() {
		return String.format(
				"Product[id=%d, name='%s']",
				productId, name);
	}
}
