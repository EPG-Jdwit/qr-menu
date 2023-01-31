package com.joshua.qrmenu.util.mocker;


import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.util.mocker.data.SubcategoryNameMocker;

public class SubcategoryMocker {

    public SubcategoryMocker() {}

    private static Long createdCounter = 0L;

    private static final SubcategoryNameMocker subcategoryNameMocker = new SubcategoryNameMocker();

    public SubcategoryEntity generateSubcategoryEntity() {
        createdCounter += 1;
        String subcategoryName = subcategoryNameMocker.subcategoryName();
        return new SubcategoryEntity(createdCounter, subcategoryName);
    }

    public NewSubcategory generateNewSubcategory() {
        createdCounter += 1;
        String categoryName = subcategoryNameMocker.subcategoryName();
        return new NewSubcategory(categoryName);
    }

    public NewSubcategory generateNullNewSubcategory() {
        createdCounter += 1;
        return new NewSubcategory(null);
    }
}
