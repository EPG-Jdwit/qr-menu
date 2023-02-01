package com.joshua.qrmenu.subcategory.service;

import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.subcategory.SubcategoryEnvironment;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;

public class PatchSubcategoryByIdTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    // TODO: placeholder file

}
