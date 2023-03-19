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

public class PatchSubcategoryByIdTest {

    private final SubcategoryEnvironment env = new SubcategoryEnvironment();

    private final SubcategoryService subcategoryService = env.initService();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    private Long setUpCategory() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        env.addCategoryEntity(categoryEntity);
        return categoryEntity.getCategoryId();
    }
    @Test
    public void updateBothNotFoundException() {

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        assertThrows(NotFoundException.class,
                () -> subcategoryService.patchSubcategoryById(0L, 0L, newSubcategory)
        );
    }

    @Test
    public void updateSubcategoryNotFoundException() {
        Long categoryId = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        assertThrows(NotFoundException.class,
                () -> subcategoryService.patchSubcategoryById(categoryId, 0L, newSubcategory)
        );
    }

    @Test
    public void updateNonExistentCategory() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        assertThrows(NotFoundException.class,
                () -> subcategoryService.patchSubcategoryById(0L, subcategory.getSubcategoryId(), newSubcategory)
        );
    }

    @Test
    public void canUpdateWithNoChanges() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        Subcategory updatedSubcategory = subcategoryService.patchSubcategoryById(categoryId, subcategory.getSubcategoryId(), newSubcategory);
        assertThat(subcategoryService.getSubcategoryById(categoryId, updatedSubcategory.getSubcategoryId())).isEqualTo(subcategory);
    }

    @Test
    public void canUpdateWithNullFields() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        NewSubcategory newNullSubcategory = subcategoryMocker.generateNullNewSubcategory();

        Subcategory updatedSubcategory = subcategoryService.patchSubcategoryById(categoryId, subcategory.getSubcategoryId(), newNullSubcategory);
        assertThat(subcategoryService.getSubcategoryById(categoryId, updatedSubcategory.getSubcategoryId())).isEqualTo(subcategory);
    }

    @Test
    public void checkUpdateNoIDChange() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);
        NewSubcategory updatedNewSubcategory = subcategoryMocker.generateNewSubcategory();

        Subcategory updatedSubcategory = subcategoryService.patchSubcategoryById(categoryId, subcategory.getSubcategoryId(), updatedNewSubcategory);
        assertThat(updatedSubcategory.getSubcategoryId()).isEqualTo(subcategory.getSubcategoryId());
    }

    @Test
    public void canUpdateName() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId, newSubcategory);

        String newName = "new name";
        newSubcategory.setName(newName);
        Subcategory updatedSubcategory = subcategoryService.patchSubcategoryById(categoryId, subcategory.getSubcategoryId(), newSubcategory);
        assertThat(updatedSubcategory.getSubcategoryId()).isEqualTo(subcategory.getSubcategoryId());
        assertThat(subcategoryService.getSubcategoryById(categoryId, updatedSubcategory.getSubcategoryId()).getName()).isNotEqualTo(subcategory.getName());
        assertThat(subcategoryService.getSubcategoryById(categoryId, updatedSubcategory.getSubcategoryId()).getName()).isEqualTo(newName);
    }

    @Test
    public void canUpdateCategory() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId1 = setUpCategory();
        Long categoryId2 = setUpCategory();

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory = subcategoryService.createNewSubcategory(categoryId1, newSubcategory);

        Subcategory updatedSubcategory = subcategoryService.patchSubcategoryById(categoryId2, subcategory.getSubcategoryId(), newSubcategory);
        assertThat(updatedSubcategory.getSubcategoryId()).isEqualTo(subcategory.getSubcategoryId());
        assertThat(subcategoryService.getSubcategoryById(categoryId2, updatedSubcategory.getSubcategoryId()).getCategoryId()).isEqualTo(categoryId2);
        // TODO: This doesn't work
//        assertThrows(NotFoundException.class,
//                () -> subcategoryService.getSubcategoryById(categoryId1, subcategory.getSubcategoryId())
//        );
    }

    @Test
    public void updateSubCategoryNameConflict() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId = setUpCategory();
        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory1 = subcategoryService.createNewSubcategory(categoryId, newSubcategory1);

        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(categoryId, newSubcategory2);
        newSubcategory2.setName(subcategory1.getName());
        assertThrows(AlreadyExistsException.class,
                () -> subcategoryService.patchSubcategoryById(categoryId, subcategory2.getSubcategoryId(), newSubcategory2));
    }

    @Test
    public void updateSubCategoryDifferentCategoryNoNameConflict() throws NotFoundException, InputException, AlreadyExistsException {
        Long categoryId1 = setUpCategory();
        Long categoryId2 = setUpCategory();
        NewSubcategory newSubcategory1 = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory1 = subcategoryService.createNewSubcategory(categoryId1, newSubcategory1);
        NewSubcategory newSubcategory2 = subcategoryMocker.generateNewSubcategory();
        Subcategory subcategory2 = subcategoryService.createNewSubcategory(categoryId2, newSubcategory2);

       newSubcategory2.setName(subcategory1.getName());
       subcategoryService.patchSubcategoryById(categoryId2, subcategory2.getSubcategoryId(), newSubcategory2);
       assertThat(subcategoryService.getSubcategoryById(categoryId2, subcategory2.getSubcategoryId()).getName()).isEqualTo(subcategory1.getName());
    }
}
