package com.joshua.qrmenu.product.service;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.product.ProductEnvironment;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteProductByIdTest {

    private final ProductEnvironment env = new ProductEnvironment();

    private final ProductService productService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    @Test
    public void deleteThrowsNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> productService.deleteProductById(0L));
    }

    @Test
    public void canDeleteById() throws NotFoundException, InputException, AlreadyExistsException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        assertThat(productService.getAll().size()).isEqualTo(1);
        productService.deleteProductById(product.getProductId());
        assertThat(productService.getAll().size()).isEqualTo(0);
    }

    @Test
    public void canDeleteWhenMultiple() throws NotFoundException, InputException, AlreadyExistsException {
        NewProduct newProduct1 = productMocker.generateNewProduct();
        NewProduct newProduct2 = productMocker.generateNewProduct();
        Product product1 = productService.createNewProduct(newProduct1);
        Product product2 = productService.createNewProduct(newProduct2);

        assertThat(productService.getAll().size()).isEqualTo(2);
        productService.deleteProductById(product1.getProductId());
        assertThat(productService.getAll().size()).isEqualTo(1);
        assertThat(productService.getAll().contains(product1)).isFalse();
        assertThat(productService.getAll().contains(product2)).isTrue();
    }
}
