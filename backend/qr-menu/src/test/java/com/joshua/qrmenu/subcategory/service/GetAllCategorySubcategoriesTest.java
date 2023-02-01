package com.joshua.qrmenu.subcategory.service;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.subcategory.SubcategoryEnvironment;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetAllCategorySubcategoriesTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void notPresentAtStart() {
        assertThrows(NotFoundException.class,
                () -> subcategoryService.getAllCategorySubcategories(0L)
        );
    }

    @Test
    public void categoryEmptyAtStart() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);

        assertThat(subcategoryService.getAllCategorySubcategories(categoryEntity.getCategoryId()).size()).isEqualTo(0);
    }

    @Test
    public void wrongCategoryId() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();


        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThrows(NotFoundException.class,
                () -> subcategoryService.getAllCategorySubcategories(0L)
        );
    }

    @Test
    public void canAddOne() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();


        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).contains(subcategory)).isTrue();
    }

    @Test
    public void canAddTwoToSameCategory() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();

        Subcategory subcategory1 = subcategoryService.createNewSubcategory(categoryId, newSubcategory1);
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(categoryId, newSubcategory2);

        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(2);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).containsAll(Arrays.asList(subcategory1, subcategory2))).isTrue();
    }

    @Test
    public void canAddTwoToDifferentCategories() throws NotFoundException {
        CategoryEntity categoryEntity1 = categoryMocker.generateCategoryEntity();
        CategoryEntity categoryEntity2 = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity1);
        env.addCategoryEntity(categoryEntity2);
        Long category1Id = categoryEntity1.getCategoryId();
        Long category2Id = categoryEntity2.getCategoryId();


        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();

        Subcategory subcategory1 = subcategoryService.createNewSubcategory(category1Id, newSubcategory1);
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(category2Id, newSubcategory2);


        assertThat(subcategoryService.getAllCategorySubcategories(category1Id).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(category2Id).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(category1Id).contains(subcategory1)).isTrue();
        assertThat(subcategoryService.getAllCategorySubcategories(category2Id).contains(subcategory2)).isTrue();

        assertThat(subcategoryService.getAllCategorySubcategories(category1Id).get(0)).
                isNotEqualTo(subcategoryService.getAllCategorySubcategories(category2Id).get(0));
    }

    @Test
    public void canAddTen() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        for (int i = 0; i < 10; i++) {
            NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
            subcategoryService.createNewSubcategory(categoryId, newSubcategory);
        }
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(10);
    }
}
