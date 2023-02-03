package com.joshua.qrmenu.product.mapper;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductMapperTest {

    private static final ProductMocker productMocker = new ProductMocker();

    private static final ProductMapper productMapper = new ProductMapper();

    private void checkEquality(ProductEntity productEntity, Product product) {
        assertThat(productEntity.getProductId()).isEqualTo(product.getProductId());
        assertThat(productEntity.getName()).isEqualTo(product.getName());
        assertThat(productEntity.getPrice()).isEqualTo(product.getPrice());
        assertThat(productEntity.getDescription()).isEqualTo(product.getDescription());
    }

    private void checkEquality(NewProduct newProduct, ProductEntity productEntity) {
        assertThat(newProduct.getName()).isEqualTo(productEntity.getName());
        assertThat(newProduct.getPrice()).isEqualTo(productEntity.getPrice());
        assertThat(newProduct.getDescription()).isEqualTo(productEntity.getDescription());
    }

    @Test
    public void checkEntityToProduct() {
        ProductEntity productEntity = productMocker.generateProductEntity();
        Product result = productMapper.entityToJson(productEntity);
        checkEquality(productEntity, result);
    }

    @Test
    public void checkEntityToProductPartialFields() {
        // Name null
        ProductEntity productEntity = productMocker.generateProductEntity();
        productEntity.setName(null);
        Product result = productMapper.entityToJson(productEntity);
        checkEquality(productEntity, result);
        // Price null
        productEntity = productMocker.generateProductEntity();
        productEntity.setPrice(null);
        result = productMapper.entityToJson(productEntity);
        checkEquality(productEntity, result);
        // Description null
        productEntity = productMocker.generateProductEntity();
        productEntity.setDescription(null);
        result = productMapper.entityToJson(productEntity);
        checkEquality(productEntity, result);
        // All null
        productEntity = productMocker.generateProductEntity();
        productEntity.setName(null);
        productEntity.setPrice(null);
        productEntity.setDescription(null);
        result = productMapper.entityToJson(productEntity);
        checkEquality(productEntity, result);

    }


    @Test
    public void checkNewProductToEntity() {
        NewProduct newProduct = productMocker.generateNewProduct();
        ProductEntity result = productMapper.newJsonToEntity(newProduct);
        checkEquality(newProduct, result);
    }

    @Test
    public void checkNewProductToEntityPartialFields() {
        // Name null
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setName(null);
        ProductEntity result = productMapper.newJsonToEntity(newProduct);
        checkEquality(newProduct, result);
        // Price null
        newProduct = productMocker.generateNewProduct();
        newProduct.setPrice(null);
        result = productMapper.newJsonToEntity(newProduct);
        checkEquality(newProduct, result);
        // Description null
        newProduct = productMocker.generateNewProduct();
        newProduct.setDescription(null);
        result = productMapper.newJsonToEntity(newProduct);
        checkEquality(newProduct, result);
        // All null
        newProduct = productMocker.generateNewProduct();
        newProduct.setName(null);
        newProduct.setPrice(null);
        newProduct.setDescription(null);
        result = productMapper.newJsonToEntity(newProduct);
        checkEquality(newProduct, result);
    }
}
