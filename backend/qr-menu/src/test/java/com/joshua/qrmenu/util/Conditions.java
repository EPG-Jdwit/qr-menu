package com.joshua.qrmenu.util;

import com.joshua.qrmenu.models.json.*;
import org.assertj.core.api.Condition;

import java.util.List;
import java.util.Objects;

public class Conditions {

    private Conditions() {}

    public static Condition<? super List<Product>> productListContainsNewProduct(NewProduct newProduct) {
        return new Condition<>(
                productList -> {
                    for (Product product : productList) {
                        if (productEqualsNewProduct(product, newProduct)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Product list should contain " + newProduct.toString()
        );
    }

    public static Condition<Product> productEqualsNewProduct(NewProduct newProduct) {
        return new Condition<>(
                product -> productEqualsNewProduct(product, newProduct),
                "Product should have the same data as " + newProduct.toString()
        );
    }

    private static boolean productEqualsNewProduct(Product product, NewProduct newProduct) {
        return Objects.equals(product.getName(), newProduct.getName()) &&
                Objects.equals(product.getPrice(), newProduct.getPrice()) &&
                Objects.equals(product.getDescription(), newProduct.getDescription());
    }

    public static Condition<? super List<Category>> categoryListContainsNewCategory(NewCategory newCategory) {
        return new Condition<>(
                categoryList -> {
                    for (Category category : categoryList) {
                        if (categoryEqualsNewCategory(category, newCategory)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Category list should contain " + newCategory.toString()
        );
    }

    public static Condition<Category> categoryEqualsNewCategory(NewCategory newCategory) {
        return new Condition<>(
                category -> categoryEqualsNewCategory(category, newCategory),
                "Category should have the same data as " + newCategory.toString()
        );
    }

    private static boolean categoryEqualsNewCategory(Category category, NewCategory newCategory) {
        return Objects.equals(category.getName(), newCategory.getName());
    }

    public static Condition<? super List<Subcategory>> subcategoryListContainsNewSubcategory(NewSubcategory newSubcategory) {
        return new Condition<>(
                subcategoryList -> {
                    for (Subcategory subcategory : subcategoryList) {
                        if (subcategoryEqualsNewSubcategory(subcategory, newSubcategory)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Subcategory list should contain " + newSubcategory.toString()
        );
    }

    public static Condition<Subcategory> subcategoryEqualsNewSubcategory(NewSubcategory newSubcategory) {
        return new Condition<>(
                subcategory -> subcategoryEqualsNewSubcategory(subcategory, newSubcategory),
                "Subcategory should have the same data as " + newSubcategory.toString()
        );
    }

    private static boolean subcategoryEqualsNewSubcategory(Subcategory subcategory, NewSubcategory newSubcategory) {
        return Objects.equals(subcategory.getName(), newSubcategory.getName());
    }
}
