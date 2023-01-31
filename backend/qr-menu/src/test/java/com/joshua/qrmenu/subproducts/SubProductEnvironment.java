package com.joshua.qrmenu.subproducts;

import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.services.SubProductService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.SubcategoryRepositoryMocker;

//TODO : ProductMapper doesn't belong here
public class SubProductEnvironment extends AbstractEnvironment<SubProductService, ProductMapper> {
    @Override
    public SubProductService initService() {
        return new SubProductService(
                ProductRepositoryMocker.init(),
                SubcategoryRepositoryMocker.init(),
                new ProductMapper(),
                new SubcategoryMapper()
        );
    }

    @Override
    public ProductMapper initMapper() {
        return null;
    }
}
