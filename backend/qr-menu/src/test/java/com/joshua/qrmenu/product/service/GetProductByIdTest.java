package com.joshua.qrmenu.product.service;

import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.product.ProductEnvironment;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static com.joshua.qrmenu.util.Conditions.productEqualsNewProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetProductByIdTest {

    private final ProductEnvironment env = new ProductEnvironment();

    private final ProductService productService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    @Test
    public void notPresentAtStart() {
        assertThrows(NotFoundException.class,
                () -> productService.getProductById(0L)
                );
    }

    @Test
    public void canGetProductById() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);
        assertThat(productService.getProductById(product.getProductId())).satisfies(productEqualsNewProduct(newProduct));
    }

    @Test
    public void getProductByWrongId() throws InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);
        assertThrows(NotFoundException.class,
                () -> productService.getProductById(product.getProductId() + 1)
        );
    }
}
