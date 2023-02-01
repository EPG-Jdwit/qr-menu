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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetSubcategoryByIdTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void notPresentAtStart() {
        assertThrows(NotFoundException.class,
                () -> subcategoryService.getSubcategoryById(0L, 0L)
        );
    }



    @Test
    public void notPresentInNewCategory() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);

        assertThrows(NotFoundException.class,
                () -> subcategoryService.getSubcategoryById(categoryEntity.getCategoryId(), 0L)
        );
    }

    @Test
    public void wrongCategoryId() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();


        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThrows(NotFoundException.class,
                () -> subcategoryService.getSubcategoryById(categoryEntity.getCategoryId() + 1L, subcategory.getSubcategoryId())
        );
    }

    @Test
    public void canAddOne() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThat(subcategoryService.getSubcategoryById(categoryId, subcategory.getSubcategoryId())).isEqualTo(subcategory);
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

        assertThat(subcategoryService.getSubcategoryById(categoryId, subcategory1.getSubcategoryId())).isEqualTo(subcategory1);
        assertThat(subcategoryService.getSubcategoryById(categoryId, subcategory2.getSubcategoryId())).isEqualTo(subcategory2);
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


        assertThat(subcategoryService.getSubcategoryById(category1Id, subcategory1.getSubcategoryId())).isEqualTo(subcategory1);
        assertThat(subcategoryService.getSubcategoryById(category2Id, subcategory2.getSubcategoryId())).isEqualTo(subcategory2);

        assertThat(subcategoryService.getAllCategorySubcategories(category1Id).get(0)).
                isNotEqualTo(subcategoryService.getAllCategorySubcategories(category2Id).get(0));
    }

    @Test
    public void canAddTen() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();
        List<Subcategory> subcategories = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
            Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);
            subcategories.add(subcategory);
        }
        for (Subcategory subcategory : subcategories) {
            assertThat(subcategoryService.getSubcategoryById(categoryId, subcategory.getSubcategoryId())).isEqualTo(subcategory);
        }
    }

    @Test
    public void notPresentAfterDelete() throws NotFoundException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);
        assertThat(subcategoryService.getSubcategoryById(categoryId, subcategory.getSubcategoryId())).isEqualTo(subcategory);

        subcategoryService.deleteSubcategoryById(categoryId, subcategory.getSubcategoryId());

        assertThrows(NotFoundException.class,
                () -> subcategoryService.getSubcategoryById(categoryEntity.getCategoryId(), subcategory.getSubcategoryId())
        );
    }
}
