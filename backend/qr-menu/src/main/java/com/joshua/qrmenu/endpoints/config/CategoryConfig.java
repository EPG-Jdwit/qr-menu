package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;

public class CategoryConfig {

    public static void populateCategories(CategoryService categoryService) throws Exception {

        categoryService.createNewCategory(new NewCategory("Bier"));
        categoryService.createNewCategory(new NewCategory("Frisdrank"));
        categoryService.createNewCategory(new NewCategory("Snack"));
        categoryService.createNewCategory(new NewCategory("Soep"));

    }
}
