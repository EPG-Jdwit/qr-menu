package com.joshua.qrmenu.product.service;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.product.ProductEnvironment;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static com.joshua.qrmenu.util.Conditions.productListContainsNewProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateNewProductTest {

    private final ProductEnvironment env = new ProductEnvironment();

    private final ProductService productService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    @Test
    public void canCreateProduct() throws InputException, AlreadyExistsException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);
        assertThat(product.getProductId()).isNotNull();
        assertThat(productService.getAll()).satisfies(productListContainsNewProduct(newProduct));
    }

    @Test
    public void createProductNoName() {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setName(null);
        assertThrows(InputException.class,
                () -> productService.createNewProduct(newProduct));
    }

    @Test
    public void createProductNoPrice() {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setPrice(null);
        assertThrows(InputException.class,
                () -> productService.createNewProduct(newProduct));
    }
}
