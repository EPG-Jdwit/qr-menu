package com.joshua.qrmenu.product.service;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.product.ProductEnvironment;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateProductTest {

    private final ProductEnvironment env = new ProductEnvironment();

    private final ProductService productService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    @Test
    public void updateNotFoundException() {
        NewProduct newProduct = productMocker.generateNewProduct();
        assertThrows(NotFoundException.class,
                () -> productService.patchProductById(0L, newProduct)
        );
    }

    @Test
    public void canUpdateWithNoChanges() throws NotFoundException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        productService.patchProductById(product.getProductId(), newProduct);
    }

    @Test
    public void canUpdateWithNullFields() throws NotFoundException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        NewProduct nullProduct = productMocker.generateNullNewProduct();
        productService.patchProductById(product.getProductId(), nullProduct);

        assertThat(productService.getProductById(product.getProductId()).getProductId()).isEqualTo(product.getProductId());
        assertThat(productService.getProductById(product.getProductId()).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductById(product.getProductId()).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductById(product.getProductId()).getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void checkUpdateNoIdChange() throws NotFoundException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);
        NewProduct updateProduct = productMocker.generateNewProduct();

        productService.patchProductById(product.getProductId(), updateProduct);
        assertThat(productService.getProductById(product.getProductId()).getProductId()).isEqualTo(product.getProductId());
    }

    @Test
    public void canUpdateProductName() throws NotFoundException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        String newName = "new name";
        newProduct.setName(newName);
        productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(product.getProductId()).getName()).isEqualTo(newName);
        assertThat(productService.getProductById(product.getProductId()).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductById(product.getProductId()).getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void canUpdateProductPrice() throws NotFoundException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        double newPrice = 5.00;
        newProduct.setPrice(newPrice);
        productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(product.getProductId()).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductById(product.getProductId()).getPrice()).isEqualTo(newPrice);
        assertThat(productService.getProductById(product.getProductId()).getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void canUpdateProductDescription() throws NotFoundException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        String newDescription = "new description";
        newProduct.setDescription(newDescription);
        productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(product.getProductId()).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductById(product.getProductId()).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductById(product.getProductId()).getDescription()).isEqualTo(newDescription);
    }

}
