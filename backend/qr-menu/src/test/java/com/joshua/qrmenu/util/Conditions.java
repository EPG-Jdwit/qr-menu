package com.joshua.qrmenu.util;

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
}
