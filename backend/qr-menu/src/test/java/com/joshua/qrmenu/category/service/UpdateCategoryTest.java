package com.joshua.qrmenu.category.service;

import com.joshua.qrmenu.category.CategoryEnvironment;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class UpdateCategoryTest {

    private final CategoryEnvironment env = new CategoryEnvironment();

    private final CategoryService categoryService = env.initService();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void updateNotFoundException() {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        assertThrows(NotFoundException.class,
                () -> categoryService.patchCategoryById(0L, newCategory)
        );
    }

    @Test
    public void canUpdateWithNoChanges() throws NotFoundException, InputException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        Category updatedCategory = categoryService.patchCategoryById(category.getCategoryId(), newCategory);
        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId())).isEqualTo(category);

    }

    @Test
    public void canUpdateWithNullFields() throws NotFoundException, InputException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        NewCategory nullCategory = categoryMocker.generateNullNewCategory();
        Category updatedCategory = categoryService.patchCategoryById(category.getCategoryId(), nullCategory);

        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId()).getCategoryId()).isEqualTo(category.getCategoryId());
        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId()).getName()).isEqualTo(category.getName());
    }

    @Test
    public void checkUpdateNoIDChange() throws NotFoundException, InputException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);
        NewCategory updatedNewCategory = categoryMocker.generateNewCategory();

        Category updatedCategory = categoryService.patchCategoryById(category.getCategoryId(), updatedNewCategory);
        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId()).getCategoryId()).isEqualTo(category.getCategoryId());
    }

    @Test
    public void canUpdateCategoryName() throws NotFoundException, InputException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        String newName = "new name";
        newCategory.setName(newName);
        Category updatedCategory = categoryService.patchCategoryById(category.getCategoryId(), newCategory);
        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId()).getCategoryId()).isEqualTo(category.getCategoryId());
        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId()).getName()).isNotEqualTo(category.getName());
        assertThat(categoryService.getCategoryById(updatedCategory.getCategoryId()).getName()).isEqualTo(newName);
    }

}
