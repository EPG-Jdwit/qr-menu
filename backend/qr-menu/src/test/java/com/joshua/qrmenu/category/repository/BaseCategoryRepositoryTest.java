package com.joshua.qrmenu.category.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class BaseCategoryRepositoryTest {

    @Autowired
    protected CategoryRepository categoryRepository;

    private final CategoryMocker categoryMocker = new CategoryMocker();

    protected CategoryEntity createCategoryEntity() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        categoryEntity.setCategoryId(null);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntity;
    }
}
