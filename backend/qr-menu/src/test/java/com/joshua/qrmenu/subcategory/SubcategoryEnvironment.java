package com.joshua.qrmenu.subcategory;


import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.CategoryRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.SubcategoryRepositoryMocker;


public class SubcategoryEnvironment extends AbstractEnvironment<SubcategoryService, SubcategoryMapper> {

    @Override
    public SubcategoryService initService() {
        return new SubcategoryService(
                SubcategoryRepositoryMocker.init(),
                // TODO: rekening houden met volgende lijn
                ProductRepositoryMocker.init(),
                CategoryRepositoryMocker.init(),
                new SubcategoryMapper()
        );
    }

    @Override
    public SubcategoryMapper initMapper() {
        return new SubcategoryMapper();
    }
}