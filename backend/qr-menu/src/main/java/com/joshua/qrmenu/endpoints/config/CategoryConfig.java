package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;

public class CategoryConfig {

    public static void populateCategories(CategoryService categoryService) throws Exception {

        categoryService.createNewCategory(new NewCategory("Bier"));
        categoryService.createNewCategory(new NewCategory("Frisdrank"));
        categoryService.createNewCategory(new NewCategory("Warme dranken"));
        categoryService.createNewCategory(new NewCategory("Aperitief"));
        categoryService.createNewCategory(new NewCategory("Wijn"));
        categoryService.createNewCategory(new NewCategory("Digestief"));
        categoryService.createNewCategory(new NewCategory("Borrelhapjes"));
        categoryService.createNewCategory(new NewCategory("Snack"));
        categoryService.createNewCategory(new NewCategory("Soep"));
        categoryService.createNewCategory(new NewCategory("Slaatjes"));
        categoryService.createNewCategory(new NewCategory("Frietgerechten"));
        categoryService.createNewCategory(new NewCategory("Pastagerechten"));
        categoryService.createNewCategory(new NewCategory("Dessert"));
        categoryService.createNewCategory(new NewCategory("Tearoom"));
        categoryService.createNewCategory(new NewCategory("Kindergerechten"));
    }
}
