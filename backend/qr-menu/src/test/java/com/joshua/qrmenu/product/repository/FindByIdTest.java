package com.joshua.qrmenu.product.repository;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindByIdTest extends BaseProductRepositoryTest {


    @Test
    public void presentAfterAdd() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.findById(productEntity.getProductId()).get()).isEqualTo(productEntity);
    }

    @Test
    public void notPresentAfterDelete() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.findById(productEntity.getProductId()).isPresent()).isTrue();
        productRepository.deleteById(productEntity.getProductId());
        assertThat(productRepository.existsById(productEntity.getProductId())).isFalse();
    }

    @Test
    public void canFindMultiple() {
        ProductEntity productEntity1 = createProductEntity();
        ProductEntity productEntity2 = createProductEntity();
        assertThat(productEntity1.getProductId()).isNotNull();
        assertThat(productEntity2.getProductId()).isNotNull();

        assertThat(productRepository.findById(productEntity1.getProductId()).isPresent()).isTrue();
        assertThat(productRepository.findById(productEntity2.getProductId()).isPresent()).isTrue();

        productRepository.deleteById(productEntity1.getProductId());
        assertThat(productRepository.findById(productEntity1.getProductId()).isPresent()).isFalse();
        assertThat(productRepository.findById(productEntity2.getProductId()).isPresent()).isTrue();
    }
}
