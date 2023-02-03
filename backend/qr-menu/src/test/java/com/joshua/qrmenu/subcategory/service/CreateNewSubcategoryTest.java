package com.joshua.qrmenu.subcategory.service;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
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

import static com.joshua.qrmenu.util.Conditions.productListContainsNewProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateNewSubcategoryTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    @Test
    public void canCreateOne() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();


        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThat(subcategory.getSubcategoryId()).isNotNull();
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).contains(subcategory)).isTrue();
    }

    @Test
    public void canCreateTwo() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        Long categoryId = categoryEntity.getCategoryId();


        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory1 = subcategoryService.createNewSubcategory(categoryId, newSubcategory1);
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(categoryId, newSubcategory2);

        assertThat(subcategory1.getSubcategoryId()).isNotNull();
        assertThat(subcategory2.getSubcategoryId()).isNotNull();
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).size()).isEqualTo(2);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryId).containsAll(Arrays.asList(subcategory1, subcategory2))).isTrue();
    }

    @Test
    public void createWithoutName() throws InputException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setName(null);
        assertThrows(InputException.class,
                () -> subcategoryService.createNewSubcategory(categoryEntity.getCategoryId(), newSubcategory));
    }

    @Test
    public void createWithNameConflictSameCategory() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);

        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        subcategoryService.createNewSubcategory(categoryEntity.getCategoryId(), newSubcategory1);

        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();
        newSubcategory2.setName(newSubcategory1.getName());
        assertThrows(AlreadyExistsException.class,
                () -> subcategoryService.createNewSubcategory(categoryEntity.getCategoryId(), newSubcategory2));
    }

    @Test
    public void createWithoutNameConflictDifferentCategory() throws NotFoundException, InputException, AlreadyExistsException {
        CategoryEntity categoryEntity1 = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity2);

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory1 = subcategoryService.createNewSubcategory(categoryEntity1.getCategoryId(), newSubcategory);
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(categoryEntity2.getCategoryId(), newSubcategory);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryEntity1.getCategoryId()).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryEntity2.getCategoryId()).size()).isEqualTo(1);
        assertThat(subcategoryService.getAllCategorySubcategories(categoryEntity1.getCategoryId()).contains(subcategory1)).isTrue();
        assertThat(subcategoryService.getAllCategorySubcategories(categoryEntity2.getCategoryId()).contains(subcategory2)).isTrue();
    }
}
