package com.joshua.qrmenu.product.service;

import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.product.ProductEnvironment;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static com.joshua.qrmenu.util.Conditions.productListContainsNewProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateNewProductTest {

    private final ProductEnvironment env = new ProductEnvironment();

    private final ProductService productService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    @Test
    public void canCreateService() {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);
        assertThat(product.getProductId()).isNotNull();
        assertThat(productService.getAll()).satisfies(productListContainsNewProduct(newProduct));
    }

    // TODO: validate fields

}
