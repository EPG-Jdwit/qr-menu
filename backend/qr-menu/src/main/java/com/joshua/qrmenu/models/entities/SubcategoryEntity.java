package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "sub_categories",
        schema = "public"
)
public class SubcategoryEntity {
    @Id
    @GeneratedValue(generator = "subcategory_generator")
    @GenericGenerator(
            name = "subcategory_generator",
            strategy = "com.joshua.qrmenu.repositories.util.IdGenerator"
    )
    @Column(
            name = "subcategory_id",
            updatable = false
    )
    private Long subcategoryId;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(
            name = "subcategory_product",
            joinColumns = @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Set<ProductEntity> products = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private CategoryEntity categoryEntity;

    public SubcategoryEntity(String name) {
        this.name = name;
    }

    public SubcategoryEntity(Long subcategoryId, String name) {
        this.subcategoryId = subcategoryId;
        this.name = name;
    }

}
