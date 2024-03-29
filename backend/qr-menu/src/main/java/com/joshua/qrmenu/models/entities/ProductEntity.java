package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * An Entity representing a Product.
 */
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

	// A List containing the only valid allergenics that can be used.
	// The values are used in the frontend to fetch the image files of the icons.
	public static final List<String> VALID_ALLERGENICS = Arrays.asList(
			"ei", "melk", "gluten", "lupine", "mosterd", "noten", "pindas",
			"schaaldieren", "selderij", "sesamzaad", "soja", "vis", "weekdieren", "zwavel"
	);

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
	private Set<SubcategoryEntity> subcategories = new HashSet<>();

	private List<String> allergenicList = new ArrayList<>();

	@Override
	public String toString() {
		return String.format(
				"Product[id=%d, name='%s']",
				productId, name);
	}
}
