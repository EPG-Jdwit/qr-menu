package com.joshua.qrmenu.product.service;

import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.product.ProductEnvironment;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllProductsTest {

    private final ProductEnvironment env = new ProductEnvironment();

    private final ProductService productService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    @Test
    public void emptyAtStart() {
        assertThat(productService.getAll().size()).isEqualTo(0);
    }

    @Test
    public void canAddOne() {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        assertThat(productService.getAll().contains(product)).isTrue();
        assertThat(productService.getAll().size()).isEqualTo(1);
    }

    @Test
    public void canAddTwo() {
        NewProduct newProduct1 = productMocker.generateNewProduct();
        NewProduct newProduct2 = productMocker.generateNewProduct();
        Product product1 = productService.createNewProduct(newProduct1);
        Product product2 = productService.createNewProduct(newProduct2);

        assertThat(productService.getAll().containsAll(new ArrayList<Product>(Arrays.asList(product1, product2)))).isTrue();
        assertThat(productService.getAll().size()).isEqualTo(2);
    }

    @Test
    public void canAddTen() {
        for (int i = 0; i < 10; i++) {
            NewProduct newProduct = productMocker.generateNewProduct();
            productService.createNewProduct(newProduct);
        }
        assertThat(productService.getAll().size()).isEqualTo(10);
    }
}
