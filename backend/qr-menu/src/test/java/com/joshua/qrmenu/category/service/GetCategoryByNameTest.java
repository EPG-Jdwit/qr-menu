package com.joshua.qrmenu.category.service;

import com.joshua.qrmenu.category.CategoryEnvironment;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import static com.joshua.qrmenu.util.Conditions.categoryEqualsNewCategory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetCategoryByNameTest {

    private final CategoryEnvironment env = new CategoryEnvironment();

    private final CategoryService categoryService = env.initService();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void notPresentAtStart() {
        assertThrows(NotFoundException.class,
                () -> categoryService.getCategoryByName("Category")
        );
    }

    @Test
    public void canGetCategoryByName() throws NotFoundException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);
        assertThat(categoryService.getCategoryByName(category.getName())).satisfies(categoryEqualsNewCategory(newCategory));
    }

    @Test
    public void getCategoryByWrongName() {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        assertThrows(NotFoundException.class,
                () -> categoryService.getCategoryByName(category.getName() + "a")
        );
    }
}
