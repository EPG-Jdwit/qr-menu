package com.joshua.qrmenu.util.mocker;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.util.mocker.data.CategoryNameMocker;

import java.util.ArrayList;
import java.util.HashSet;

public class CategoryMocker {

    public CategoryMocker() {}

    private static Long createdCounter = 0L;
    private static int orderNumber = 0;

    private static final CategoryNameMocker categoryNameMocker= new CategoryNameMocker();

    public CategoryEntity generateCategoryEntity() {
        createdCounter += 1;
        orderNumber += 1;
        String categoryName = categoryNameMocker.categoryName();
        return new CategoryEntity(createdCounter, categoryName, new ArrayList<>(), orderNumber);
    }

    public NewCategory generateNewCategory() {
        createdCounter += 1;
        orderNumber += 1;
        String categoryName = categoryNameMocker.categoryName();
        return new NewCategory(categoryName, orderNumber);
    }

    public NewCategory generateNullNewCategory() {
        createdCounter += 1;
        orderNumber += 1;
        return new NewCategory(null, orderNumber);
    }
}
