package com.joshua.qrmenu.util;

import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
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

    private static boolean categoryEqualsNewCategory(Category category, NewCategory newCategory) {
        return Objects.equals(category.getName(), newCategory.getName());
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
                "Product list should contain " + newCategory.toString()
        );
    }

    public static Condition<Category> categoryEqualsNewCategory(NewCategory newCategory) {
        return new Condition<>(
                category -> categoryEqualsNewCategory(category, newCategory),
                "Product should have the same data as " + newCategory.toString()
        );
    }
}