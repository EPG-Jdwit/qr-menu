package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.SubcategoryRepository;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class BaseSubcategoryRepositoryTest {

    // Required as subcategories need an existing category
    @Autowired
    protected CategoryRepository categoryRepository;

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Autowired
    protected SubcategoryRepository subcategoryRepository;

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    protected CategoryEntity createCategory1() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        categoryEntity.setCategoryId(null);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntity;
    }

    protected CategoryEntity createCategory2() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        categoryEntity.setCategoryId(null);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntity;
    }

    protected SubcategoryEntity createSubcategoryEntity(CategoryEntity categoryEntity) {
        SubcategoryEntity subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
        subcategoryEntity.setCategoryEntity(categoryEntity);
        subcategoryEntity = subcategoryRepository.save(subcategoryEntity);
        return subcategoryEntity;
    }
}
