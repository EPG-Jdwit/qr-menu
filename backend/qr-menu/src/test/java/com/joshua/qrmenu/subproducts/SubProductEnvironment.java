package com.joshua.qrmenu.subproducts;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.repositories.SubcategoryRepository;
import com.joshua.qrmenu.services.SubProductService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.CategoryRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.SubcategoryRepositoryMocker;

//TODO : ProductMapper doesn't belong here
public class SubProductEnvironment extends AbstractEnvironment<SubProductService> {

    private final ProductRepository productRepository = ProductRepositoryMocker.init();
    private final SubcategoryRepository subcategoryRepository = SubcategoryRepositoryMocker.init();
    private final CategoryRepository categoryRepository = CategoryRepositoryMocker.init();


    @Override
    public SubProductService initService() {
        return new SubProductService(
                productRepository,
                subcategoryRepository,
                new ProductMapper(),
                new SubcategoryMapper()
        );
    }

    public void addProductEntity(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    public void addCategoryEntity(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }

    public void addSubcategoryEntity(SubcategoryEntity subcategoryEntity) {
        subcategoryRepository.save(subcategoryEntity);
    }

}
