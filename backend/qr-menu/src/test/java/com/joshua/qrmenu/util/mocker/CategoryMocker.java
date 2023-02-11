package com.joshua.qrmenu.util.mocker;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.util.mocker.data.CategoryNameMocker;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Mocker for categories.
 */
public class CategoryMocker {

    public CategoryMocker() {}

    private static Long createdCounter = 0L;
    private static int orderNumber = 0;

    private static final CategoryNameMocker categoryNameMocker= new CategoryNameMocker();

    /**
     * Mocks a new CategoryEntity.
     * @return : A newly mocker CategoryEntity.
     */
    public CategoryEntity generateCategoryEntity() {
        createdCounter += 1;
        orderNumber += 1;
        String categoryName = categoryNameMocker.categoryName();
        return new CategoryEntity(createdCounter, categoryName, new ArrayList<>(), orderNumber);
    }

    /**
     * Mocks a new NewCategory.
     * @return : A newly mocked NewCategory.
     */
    public NewCategory generateNewCategory() {
        createdCounter += 1;
        orderNumber += 1;
        String categoryName = categoryNameMocker.categoryName();
        return new NewCategory(categoryName, orderNumber);
    }

    /**
     * Mocks a new NewCategory with all fields set to null.
     * @return : A newly mocked NewCategory with all fields set to null.
     */
    public NewCategory generateNullNewCategory() {
        createdCounter += 1;
        orderNumber += 1;
        return new NewCategory(null, orderNumber);
    }
}
