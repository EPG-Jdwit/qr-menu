package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;

public class CategoryConfig {

    public static void populateCategories(CategoryService categoryService) throws Exception {
        for (int i = 0; i < categoryNames.length; i++) {
            categoryService.createNewCategory(new NewCategory(categoryNames[i], i));
        }
    }

    private static final String[] categoryNames = new String[] {
            "Bier",
            "Frisdrank",
            "Warme dranken",
            "Aperitief",
            "Wijn",
            "Digestief",
            "Borrelhapjes",
            "Snack",
            "Soep",
            "Slaatjes",
            "Frietgerechten",
            "Pastagerechten",
            "Dessert",
            "Kindergerechten"
    };
}
