package com.joshua.qrmenu.subcategory.service;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.subcategory.SubcategoryEnvironment;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteSubcategoryByIdTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void deleteThrowsNotFoundExceptionNonExistingCategory() {
        assertThrows(NotFoundException.class,
                () -> subcategoryService.deleteSubcategoryById(0L, 0L));
    }

    @Test
    public void deleteThrowsNotFoundExceptionNonExistingSubcategory() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        assertThrows(NotFoundException.class,
                () -> subcategoryService.deleteSubcategoryById(categoryId, 0L));
    }
    @Test
    public void deleteThrowsNotFoundExceptionWrongCategory() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThrows(NotFoundException.class,
                () -> subcategoryService.deleteSubcategoryById(categoryId + 1, subcategory.getSubcategoryId()));
    }

    @Test
    public void canDeleteById() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(1);
        subcategoryService.deleteSubcategoryById(categoryId, subcategory.getSubcategoryId());
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(0);
    }

    @Test
    public void canDeleteWhenMultiple() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory1 = subcategoryService.createNewSubcategory(categoryId, newSubcategory1);
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(categoryId, newSubcategory2);

        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(2);
        subcategoryService.deleteSubcategoryById(categoryId, subcategory1.getSubcategoryId());
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).contains(subcategory1)).isFalse();
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).contains(subcategory2)).isTrue();
    }




}
