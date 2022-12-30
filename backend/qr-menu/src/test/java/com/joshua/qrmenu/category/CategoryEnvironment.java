package com.joshua.qrmenu.category;

import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.CategoryRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;

public class CategoryEnvironment extends AbstractEnvironment<CategoryService, CategoryMapper> {

    @Override
    public CategoryService initService() {
        return new CategoryService(
                CategoryRepositoryMocker.init(),
                // TODO: rekening houden met volgende lijn
                ProductRepositoryMocker.init(),
                new CategoryMapper()
        );
    }

    @Override
    public CategoryMapper initMapper() {
        return new CategoryMapper();
    }
}