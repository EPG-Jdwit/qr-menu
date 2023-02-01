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
        name = "categories",
        schema = "public"
)
public class CategoryEntity {

    @Id
    @GeneratedValue(generator = "cat_generator")
    @GenericGenerator(
            name = "cat_generator",
            strategy = "com.joshua.qrmenu.repositories.util.IdGenerator"
    )
    @Column(
            name = "category_id",
            updatable = false
    )
    private Long categoryId;

    private String name;

    @OneToMany(
            mappedBy = "categoryEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SubcategoryEntity> subcategoryEntities = new HashSet<>();

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
