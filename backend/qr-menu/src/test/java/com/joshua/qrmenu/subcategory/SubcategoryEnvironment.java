package com.joshua.qrmenu.subcategory;


import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.repositories.SubcategoryRepository;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.CategoryRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.SubcategoryRepositoryMocker;


public class SubcategoryEnvironment extends AbstractEnvironment<SubcategoryService, SubcategoryMapper> {

    private final SubcategoryRepository subcategoryRepository = SubcategoryRepositoryMocker.init();
    private final CategoryRepository categoryRepository = CategoryRepositoryMocker.init();
    private final ProductRepository productRepository = ProductRepositoryMocker.init();

    private SubcategoryMapper subcategoryMapper;
    @Override
    public SubcategoryService initService() {
        return new SubcategoryService(
                subcategoryRepository,
                productRepository,
                categoryRepository,
                initMapper()
        );
    }

    @Override
    public SubcategoryMapper initMapper() {
        if (subcategoryMapper == null) {
            subcategoryMapper = new SubcategoryMapper();
        }
        return subcategoryMapper;
    }

    public void addCategoryEntity(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }
}