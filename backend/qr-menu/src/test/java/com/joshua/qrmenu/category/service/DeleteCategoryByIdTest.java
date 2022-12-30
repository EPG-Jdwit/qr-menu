package com.joshua.qrmenu.category.service;

import com.joshua.qrmenu.category.CategoryEnvironment;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DeleteCategoryByIdTest {

    private final CategoryEnvironment env = new CategoryEnvironment();

    private final CategoryService categoryService = env.initService();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void deleteThrowsNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> categoryService.deleteCategoryById(0L));
    }

    @Test
    public void canDeleteById() throws NotFoundException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        assertThat(categoryService.getAll().size()).isEqualTo(1);
        categoryService.deleteCategoryById(category.getCategoryId());
        assertThat(categoryService.getAll().size()).isEqualTo(0);
    }

    @Test
    public void canDeleteWhenMultiple() throws NotFoundException {
        NewCategory newCategory1 = categoryMocker.generateNewCategory();
        NewCategory newCategory2 = categoryMocker.generateNewCategory();
        Category category1 = categoryService.createNewCategory(newCategory1);
        Category category2 = categoryService.createNewCategory(newCategory2);

        assertThat(categoryService.getAll().size()).isEqualTo(2);
        categoryService.deleteCategoryById(category1.getCategoryId());
        assertThat(categoryService.getAll().size()).isEqualTo(1);
        assertThat(categoryService.getAll().contains(category1)).isFalse();
        assertThat(categoryService.getAll().contains(category2)).isTrue();
    }
}
