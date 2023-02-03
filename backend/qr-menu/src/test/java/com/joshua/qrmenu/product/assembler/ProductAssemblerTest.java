package com.joshua.qrmenu.product.assembler;

import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductAssemblerTest {

    private static final ProductMocker productMocker = new ProductMocker();

    private static final ProductMapper productMapper = new ProductMapper();

    private static final ProductAssembler productAssembler = new ProductAssembler();

    @Test
    public void testSingleProduct() {
        ProductEntity productEntity = productMocker.generateProductEntity();
        Product product = productMapper.entityToJson(productEntity);
        EntityModel<Product> result = productAssembler.toModel(product);

        // NOTE: This only tests if the generated values from the mocker are returned
        assertThat(result.getContent().getProductId()).isNotNull();
        assertThat(result.getContent().getName()).isNotNull();
        assertThat(result.getContent().getPrice()).isNotNull();
        assertThat(result.getContent().getDescription()).isNotNull();

        assertThat(result.getLink("self").isPresent()).isTrue();
        assertThat(result.getLink("products").isPresent()).isTrue();
    }

    @Test
    public void testMultipleProducts() {
        ProductEntity productEntity1 = productMocker.generateProductEntity();
        ProductEntity productEntity2 = productMocker.generateProductEntity();
        Product product1 = productMapper.entityToJson(productEntity1);
        Product product2 = productMapper.entityToJson(productEntity2);

        CollectionModel<EntityModel<Product>> result = productAssembler.toCollectionModel(Arrays.asList(product1, product2));
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }

    @Test
    public void testEmptyMultipleProducts() {
        CollectionModel<EntityModel<Product>> result = productAssembler.toCollectionModel(new ArrayList<>());
        assertThat(result.getContent().size()).isEqualTo(0);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }
}
