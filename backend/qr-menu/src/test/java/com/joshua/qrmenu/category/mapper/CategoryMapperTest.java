package com.joshua.qrmenu.category.mapper;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryMapperTest {

    private static final CategoryMocker categoryMocker = new CategoryMocker();

    private static final CategoryMapper categoryMapper = new CategoryMapper();

    private void checkEquality(CategoryEntity categoryEntity, Category category) {
        assertThat(categoryEntity.getCategoryId()).isEqualTo(category.getCategoryId());
        assertThat(categoryEntity.getName()).isEqualTo(category.getName());
    }

    private void checkEquality(NewCategory newCategory, CategoryEntity categoryEntity) {
        assertThat(newCategory.getName()).isEqualTo(categoryEntity.getName());
    }

    @Test
    public void checkEntityToCategory() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        Category result = categoryMapper.entityToJson(categoryEntity);
        checkEquality(categoryEntity, result);
    }

    @Test
    public void checkEntityToCategoryPartialFields() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        categoryEntity.setName(null);
        Category result = categoryMapper.entityToJson(categoryEntity);
        checkEquality(categoryEntity, result);
    }

    @Test
    public void checkNewCategoryToEntity() {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        CategoryEntity categoryEntity = categoryMapper.newJsonToEntity(newCategory);
        checkEquality(newCategory, categoryEntity);
    }

    @Test
    public void checkNewCategoryToEntityPartialFields() {
        NewCategory newCategory = categoryMocker.generateNewCategory();
        newCategory.setName(null);
        CategoryEntity categoryEntity = categoryMapper.newJsonToEntity(newCategory);
        checkEquality(newCategory, categoryEntity);
    }
}
