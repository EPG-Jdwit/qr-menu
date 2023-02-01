package com.joshua.qrmenu.product;

import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;

public class ProductEnvironment extends AbstractEnvironment<ProductService> {

    @Override
    public ProductService initService() {
        return new ProductService(
                ProductRepositoryMocker.init(),
                new ProductMapper()
        );
    }
}
