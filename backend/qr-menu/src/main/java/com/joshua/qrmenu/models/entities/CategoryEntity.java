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

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
//    @JoinTable(
//            name = "category_subcategory",
//            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"),
//            inverseJoinColumns = @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id"),
//            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
//            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
//    )
//    private Set<SubcategoryEntity> products;
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
