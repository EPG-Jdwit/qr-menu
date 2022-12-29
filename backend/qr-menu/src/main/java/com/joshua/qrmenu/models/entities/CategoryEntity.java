package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(
            name = "category_id",
            updatable = false
    )
    private Long categoryId;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Set<ProductEntity> products;

    public CategoryEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Category[id=%d, name='%s']",
                categoryId, name);
    }

//    public void preAddProductEntity(ProductEntity productEntity) {
//        products.add(productEntity);
//    }
//
//    public void addProductEntity(ProductEntity productEntity) {
//        preAddProductEntity(productEntity);
//        productEntity.preAddCategoryEntity(this);
//    }
//
//    public void preRemoveProductEntity(ProductEntity productEntity) {
//        products.remove(productEntity);
//    }
//
//    public void removeProductEntity(ProductEntity productEntity) {
//        preRemoveProductEntity(productEntity);
//        productEntity.preRemoveCategoryEntity(this);
//    }

}
