package com.joshua.qrmenu.product.repository;

import com.joshua.qrmenu.models.entities.ProductEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindByNameTest extends BaseProductRepositoryTest {

    @Test
    public void presentAfterAdd() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.findByName(productEntity.getName()).get()).isEqualTo(productEntity);
    }


    @Test
    public void notPresentAfterDelete() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.findByName(productEntity.getName()).get()).isEqualTo(productEntity);
        productRepository.deleteById(productEntity.getProductId());
        assertThat(productRepository.findByName(productEntity.getName()).isPresent()).isFalse();
    }

    @Test
    public void canFindMultiple() {
        ProductEntity productEntity1 = createProductEntity();
        ProductEntity productEntity2 = createProductEntity();
        assertThat(productEntity1.getProductId()).isNotNull();
        assertThat(productEntity2.getProductId()).isNotNull();

        assertThat(productRepository.findByName(productEntity1.getName()).get()).isEqualTo(productEntity1);
        assertThat(productRepository.findByName(productEntity2.getName()).get()).isEqualTo(productEntity2);

        productRepository.deleteById(productEntity1.getProductId());
        assertThat(productRepository.findByName(productEntity1.getName()).isPresent()).isFalse();
        assertThat(productRepository.findByName(productEntity2.getName()).isPresent()).isTrue();
    }
}
