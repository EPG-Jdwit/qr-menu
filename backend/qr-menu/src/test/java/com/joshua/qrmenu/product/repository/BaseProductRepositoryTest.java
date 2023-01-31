package com.joshua.qrmenu.product.repository;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BaseProductRepositoryTest {

    @Autowired
    protected ProductRepository productRepository;

    private final ProductMocker productMocker = new ProductMocker();

    protected ProductEntity createProductEntity() {
        ProductEntity productEntity = productMocker.generateProductEntity();
        productEntity.setProductId(null);
        productEntity = productRepository.save(productEntity);
        return productEntity;
    }
}
