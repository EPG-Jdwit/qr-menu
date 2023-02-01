package com.joshua.qrmenu.subcategory.service;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.subcategory.SubcategoryEnvironment;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.Test;

public class CreateNewSubcategoryTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void canCreateOne() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();


        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);
    }

    // TODO: Without required fields, with required fields but not optional ones
}
