package com.joshua.qrmenu.util;

import com.joshua.qrmenu.models.json.*;
import org.assertj.core.api.Condition;

import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Conditions {

    private Conditions() {}

    /**
     * Checks if a list of Products contains the equivalent NewProduct.
     *
     * @param newProduct : The NewProduct to be searched.
     * @return : A new Condition that tests if the predicate is met: Product List containing NewProduct.
     */
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

    /**
     * Checks if a Product is equivalent to a NewProduct.
     *
     * @param newProduct : The NewProduct to be tested.
     * @return : A new Condition that tests if the predicate is met: Product equivalent to NewProduct.
     */
    public static Condition<Product> productEqualsNewProduct(NewProduct newProduct) {
        return new Condition<>(
                product -> productEqualsNewProduct(product, newProduct),
                "Product should have the same data as " + newProduct.toString()
        );
    }

    /**
     * Checks if a Product is equivalent to a New Product.
     * @param product : A Product.
     * @param newProduct : A NewProduct.
     * @return : Boolean indicating equality.
     */
    private static boolean productEqualsNewProduct(Product product, NewProduct newProduct) {
        return Objects.equals(product.getName(), newProduct.getName()) &&
                Objects.equals(product.getPrice(), newProduct.getPrice()) &&
                Objects.equals(product.getDescription(), newProduct.getDescription());
    }

    /**
     * Checks if a list of Categories contains the equivalent NewCategory.
     *
     * @param newCategory : The NewCategory to be searched.
     * @return : A new Condition that tests if the predicate is met: Category List containing NewCategory.
     */
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

    /**
     * Checks if a Category is equivalent to a NewCategory.
     *
     * @param newCategory : The NewCategory to be tested.
     * @return : A new Condition that tests if the predicate is met: Category equivalent to NewCategory.
     */
    public static Condition<Category> categoryEqualsNewCategory(NewCategory newCategory) {
        return new Condition<>(
                category -> categoryEqualsNewCategory(category, newCategory),
                "Category should have the same data as " + newCategory.toString()
        );
    }

    /**
     * Checks if a Category is equivalent to a NewCategory.
     *
     * @param category : A Category.
     * @param newCategory : A NewCategory.
     * @return : Boolean indicating equality.
     */
    private static boolean categoryEqualsNewCategory(Category category, NewCategory newCategory) {
        return Objects.equals(category.getName(), newCategory.getName());
    }

    /**
     * Checks if a list of Subcategories contains the equivalent NewSubcategory.
     *
     * @param newSubcategory : The NewSubcategory to be searched.
     * @return : A new Condition that tests if the predicate is met: Subcategory List containing NewSubcategory.
     */
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

    /**
     * Checks if a Subcategory is equivalent to a NewSubcategory.
     *
     * @param newSubcategory : The NewSubcategory to be tested.
     * @return : A new Condition that tests if the predicate is met: Subcategory equivalent to NewSubcategory.
     */
    public static Condition<Subcategory> subcategoryEqualsNewSubcategory(NewSubcategory newSubcategory) {
        return new Condition<>(
                subcategory -> subcategoryEqualsNewSubcategory(subcategory, newSubcategory),
                "Subcategory should have the same data as " + newSubcategory.toString()
        );
    }

    /**
     * Checks if a Subcategory is equivalent to a NewSubcategory.
     *
     * @param subcategory : A Subcategory.
     * @param newSubcategory : A NewSubcategory.
     * @return : Boolean indicating equality.
     */
    private static boolean subcategoryEqualsNewSubcategory(Subcategory subcategory, NewSubcategory newSubcategory) {
        return Objects.equals(subcategory.getName(), newSubcategory.getName());
    }
}
