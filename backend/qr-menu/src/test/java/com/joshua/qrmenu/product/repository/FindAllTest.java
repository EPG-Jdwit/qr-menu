package com.joshua.qrmenu.product.repository;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FindAllTest {

    @Autowired
    private ProductRepository productRepository;

    private final ProductMocker productMocker = new ProductMocker();

    private ProductEntity createProductEntity() {
        ProductEntity productEntity = productMocker.generateProductEntity();
        productEntity.setProductId(null);
        productEntity = productRepository.save(productEntity);
        return productEntity;
    }

    @Test
    public void emptyAtStart() {
        assertThat(productRepository.findAll().isEmpty()).isTrue();
        assertThat(productRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void canAddOne() {
        ProductEntity productEntity = createProductEntity();
        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(productRepository.findAll().contains(productEntity)).isTrue();
    }

    @Test
    public void canAddTwo() {
        ProductEntity productEntity1 = createProductEntity();
        ProductEntity productEntity2 = createProductEntity();
        assertThat(productRepository.findAll().size()).isEqualTo(2);
        assertThat(productRepository.findAll().containsAll(Arrays.asList(productEntity1, productEntity2))).isTrue();
    }

    @Test
    public void canAddTen() {
        for (int i = 0; i < 10; i++) {
            createProductEntity();
        }
        assertThat(productRepository.findAll().size()).isEqualTo(10);
    }

    //TODO: make own test suite
    @Test
    public void notPresentAfterDelete() {
        assertThat(productRepository.findAll().size()).isEqualTo(0);
        ProductEntity productEntity = createProductEntity();
        assertThat(productEntity.getProductId()).isNotNull();
        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(productRepository.findAll().contains(productEntity)).isTrue();
        productRepository.deleteById(productEntity.getProductId());
        assertThat(productRepository.findAll().contains(productEntity)).isFalse();
        assertThat(productRepository.findAll().size()).isEqualTo(0);

    }

}