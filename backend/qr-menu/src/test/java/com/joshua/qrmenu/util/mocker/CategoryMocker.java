package com.joshua.qrmenu.util.mocker;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.util.mocker.data.CategoryNameMocker;

public class CategoryMocker {

    public CategoryMocker() {}

    private static Long createdCounter = 0L;

    private static final CategoryNameMocker categoryNameMocker= new CategoryNameMocker();

    public CategoryEntity generateCategoryEntity() {
        createdCounter += 1;
        String categoryName = categoryNameMocker.categoryName();
        return new CategoryEntity(createdCounter, categoryName, null);
    }

    public NewCategory generateNewCategory() {
        createdCounter += 1;
        String categoryName = categoryNameMocker.categoryName();
        return new NewCategory(categoryName, null);
    }

    public NewCategory generateNullNewCategory() {
        createdCounter += 1;
        return new NewCategory(null, null);
    }
}
