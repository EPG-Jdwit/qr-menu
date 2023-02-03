package com.joshua.qrmenu.category.service;

import com.joshua.qrmenu.category.CategoryEnvironment;
import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import static com.joshua.qrmenu.util.Conditions.categoryEqualsNewCategory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetCategoryByIdTest {

    private final CategoryEnvironment env = new CategoryEnvironment();

    private final CategoryService categoryService = env.initService();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void notPresentAtStart() {
        assertThrows(NotFoundException.class,
                () -> categoryService.getCategoryById(0L)
                );
    }

    @Test
    public void canGetCategoryById() throws NotFoundException, InputException, AlreadyExistsException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        assertThat(categoryService.getCategoryById(category.getCategoryId())).satisfies(categoryEqualsNewCategory(newCategory));
    }

    @Test
    public void getCategoryByWrongID() throws InputException, AlreadyExistsException {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        Category category = categoryService.createNewCategory(newCategory);

        assertThrows(NotFoundException.class,
                () -> categoryService.getCategoryById(category.getCategoryId() + 1)
        );
    }
}
