package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(
            name = "category_id",
            updatable = false
    )
    private Long categoryId;

    private String name;

    @ManyToMany()
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    )
    private Set<ProductEntity> products;

    protected CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Category[id=%d, name='%s']",
                categoryId, name);
    }

}
