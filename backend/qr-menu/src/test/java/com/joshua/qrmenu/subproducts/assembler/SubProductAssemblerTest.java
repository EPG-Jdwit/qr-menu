package com.joshua.qrmenu.subproducts.assembler;

import com.joshua.qrmenu.endpoints.assemblers.SubProductAssembler;
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

public class SubProductAssemblerTest {

    private static final ProductMocker productMocker = new ProductMocker();
    private static final ProductMapper productMapper = new ProductMapper();
    private static final SubProductAssembler subProductAssembler = new SubProductAssembler();

    @Test
    public void checkMultipleSubcategoryProducts() {
        ProductEntity productEntity1 = productMocker.generateProductEntity();
        ProductEntity productEntity2 = productMocker.generateProductEntity();
        Product product1 = productMapper.entityToJson(productEntity1);
        Product product2 = productMapper.entityToJson(productEntity2);

        CollectionModel<EntityModel<Product>> result = subProductAssembler.toCollectionModel(Arrays.asList(product1, product2), 1L, 1L);
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }

    @Test
    public void checkEmptyMultipleSubcategoryProducts() {
        CollectionModel<EntityModel<Product>> result = subProductAssembler.toCollectionModel(new ArrayList<>(), 1L, 1L);
        assertThat(result.getContent().size()).isEqualTo(0);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }
}
