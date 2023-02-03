package com.joshua.qrmenu.category.service;

import com.joshua.qrmenu.category.CategoryEnvironment;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import static com.joshua.qrmenu.util.Conditions.categoryListContainsNewCategory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateNewCategoryTest {

    private final CategoryEnvironment env = new CategoryEnvironment();

    private final CategoryService categoryService = env.initService();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void canCreateCategory() throws InputException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);
        assertThat(category.getCategoryId()).isNotNull();
        assertThat(categoryService.getAll()).satisfies(categoryListContainsNewCategory(newCategory));
    }

    @Test
    public void createCategoryWithoutName() throws InputException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        newCategory.setName(null);
        assertThrows(InputException.class,
                () -> categoryService.createNewCategory(newCategory));
    }
}
