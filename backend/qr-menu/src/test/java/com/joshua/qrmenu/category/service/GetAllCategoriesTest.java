package com.joshua.qrmenu.category.service;

import com.joshua.qrmenu.category.CategoryEnvironment;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllCategoriesTest {

    private final CategoryEnvironment env = new CategoryEnvironment();

    private final CategoryService categoryService = env.initService();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void emptyAtStart() {
        assertThat(categoryService.getAll().size()).isEqualTo(0);
    }

    @Test
    public void canAddOne() {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        assertThat(categoryService.getAll().size()).isEqualTo(1);
        assertThat(categoryService.getAll().contains(category)).isTrue();
    }

    @Test
    public void canAddTwo() {
        NewCategory newCategory1 = categoryMocker.generateNewCategory();
        NewCategory newCategory2 = categoryMocker.generateNewCategory();
        Category category1 = categoryService.createNewCategory(newCategory1);
        Category category2 = categoryService.createNewCategory(newCategory2);

        assertThat(categoryService.getAll().size()).isEqualTo(2);
        assertThat(categoryService.getAll().containsAll(Arrays.asList(category1, category2))).isTrue();
    }

    @Test
    public void canAddTen() {
        for (int i = 0; i < 10; i++) {
            NewCategory newCategory = categoryMocker.generateNewCategory();
            categoryService.createNewCategory(newCategory);
        }
        assertThat(categoryService.getAll().size()).isEqualTo(10);
    }
}
