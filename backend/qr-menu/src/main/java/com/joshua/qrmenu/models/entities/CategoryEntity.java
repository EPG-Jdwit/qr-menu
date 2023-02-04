package com.joshua.qrmenu.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "categories",
        schema = "public",
        indexes = {
                @Index(
                        name = "category_order_nr_index",
                        columnList = "order_nr"
                )
        }
)
public class CategoryEntity {

    public static final int MAX_NAME_LENGTH = 255; // (default) //TODO: JdbcSQLDataException

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

    @Basic(optional = false)
    @Column(
            name= "name",
            nullable = false,
            length = MAX_NAME_LENGTH
    )
    private String name;

    @OneToMany(
            mappedBy = "categoryEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SubcategoryEntity> subcategoryEntities = new ArrayList<>();

    @Basic(optional = false)
    @Column(
            name = "order_nr",
            nullable = false
    )
    private int orderNr;

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
