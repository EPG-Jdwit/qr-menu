package com.joshua.qrmenu.util.mocker;


import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.util.mocker.data.SubcategoryNameMocker;

/**
 * Mocker for subcategories.
 */
public class SubcategoryMocker {

    public SubcategoryMocker() {}

    private static Long createdCounter = 0L;

    private static final SubcategoryNameMocker subcategoryNameMocker = new SubcategoryNameMocker();

    /**
     * Mocks a new SubcategoryEntity.
     * @return : A newly mocker SubcategoryEntity.
     */
    public SubcategoryEntity generateSubcategoryEntity() {
        createdCounter += 1;
        String subcategoryName = subcategoryNameMocker.subcategoryName();
        return new SubcategoryEntity(createdCounter, subcategoryName);
    }

    /**
     * Mocks a new NewSubcategory.
     * @return : A newly mocked NewSubcategory.
     */
    public NewSubcategory generateNewSubcategory() {
        createdCounter += 1;
        String categoryName = subcategoryNameMocker.subcategoryName();
        return new NewSubcategory(categoryName);
    }

    /**
     * Mocks a new NewSubcategory with all fields set to null.
     * @return : A newly mocked NewSubcategory with all fields set to null.
     */
    public NewSubcategory generateNullNewSubcategory() {
        createdCounter += 1;
        return new NewSubcategory(null);
    }
}
