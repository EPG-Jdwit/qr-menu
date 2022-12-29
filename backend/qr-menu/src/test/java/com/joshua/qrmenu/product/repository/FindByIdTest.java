package com.joshua.qrmenu.product.repository;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
public class FindByIdTest {

    @Autowired
    private ProductRepository productRepository;

    private final ProductMocker productMocker = new ProductMocker();

    private ProductEntity createProductEntity() {
        ProductEntity productEntity = productMocker.productEntity();
        productEntity.setProductId(null);
        productEntity = productRepository.save(productEntity);
        return productEntity;
    }


    @Test
    public void presentAfterAdd() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.existsById(productEntity.getProductId())).isTrue();
    }

    @Test
    public void notPresentAfterDelete() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.existsById(productEntity.getProductId())).isTrue();
        productRepository.deleteById(productEntity.getProductId());
        assertThat(productRepository.existsById(productEntity.getProductId())).isFalse();
    }

    @Test
    public void canFindMultiple() {
        ProductEntity productEntity1 = createProductEntity();
        ProductEntity productEntity2 = createProductEntity();
        assertThat(productEntity1.getProductId()).isNotNull();
        assertThat(productEntity2.getProductId()).isNotNull();

        assertThat(productRepository.existsById(productEntity1.getProductId())).isTrue();
        assertThat(productRepository.existsById(productEntity2.getProductId())).isTrue();

        productRepository.deleteById(productEntity1.getProductId());
        assertThat(productRepository.existsById(productEntity1.getProductId())).isFalse();
        assertThat(productRepository.existsById(productEntity2.getProductId())).isTrue();
    }
}
