package com.joshua.qrmenu.product.service;

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
    public void canUpdateWithNoChanges() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(product.getProductId())).isEqualTo(product);
    }

    @Test
    public void canUpdateWithNullFields() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        NewProduct nullProduct = productMocker.generateNullNewProduct();
        Product updatedProduct = productService.patchProductById(product.getProductId(), nullProduct);

        assertThat(productService.getProductById(updatedProduct.getProductId()).getProductId()).isEqualTo(product.getProductId());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void checkUpdateNoIdChange() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);
        NewProduct updateNewProduct = productMocker.generateNewProduct();

        Product updatedProduct = productService.patchProductById(product.getProductId(), updateNewProduct);
        assertThat(productService.getProductById(updatedProduct.getProductId()).getProductId()).isEqualTo(product.getProductId());
    }

    @Test
    public void canUpdateProductName() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        String newName = "new name";
        newProduct.setName(newName);
        Product updatedProduct = productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(updatedProduct.getProductId()).getProductId()).isEqualTo(product.getProductId());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getName()).isNotEqualTo(product.getName());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getName()).isEqualTo(newName);
        assertThat(productService.getProductById(updatedProduct.getProductId()).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void canUpdateProductPrice() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        double newPrice = 5.00;
        newProduct.setPrice(newPrice);
        Product updatedProduct = productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(updatedProduct.getProductId()).getProductId()).isEqualTo(product.getProductId());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getPrice()).isNotEqualTo(product.getPrice());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getPrice()).isEqualTo(newPrice);
        assertThat(productService.getProductById(updatedProduct.getProductId()).getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void canUpdateProductDescription() throws NotFoundException, InputException {
        NewProduct newProduct = productMocker.generateNewProduct();
        Product product = productService.createNewProduct(newProduct);

        String newDescription = "new description";
        newProduct.setDescription(newDescription);
        Product updatedProduct = productService.patchProductById(product.getProductId(), newProduct);
        assertThat(productService.getProductById(updatedProduct.getProductId()).getProductId()).isEqualTo(product.getProductId());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getDescription()).isNotEqualTo(product.getDescription());
        assertThat(productService.getProductById(updatedProduct.getProductId()).getDescription()).isEqualTo(newDescription);
    }

}
