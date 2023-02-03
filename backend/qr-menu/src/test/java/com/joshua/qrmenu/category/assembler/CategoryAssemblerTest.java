package com.joshua.qrmenu.category.assembler;

import com.joshua.qrmenu.endpoints.assemblers.CategoryAssembler;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryAssemblerTest {

    private static final CategoryMocker categoryMocker = new CategoryMocker();

    private static final CategoryMapper categoryMapper = new CategoryMapper();

    private static final CategoryAssembler categoryAssembler = new CategoryAssembler();

    @Test
    public void testSingleCategory() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        Category category = categoryMapper.entityToJson(categoryEntity);

        EntityModel<Category> result = categoryAssembler.toModel(category);

        // NOTE: This only tests if the generated values from the mocker are returned
        assertThat(result.getContent().getCategoryId()).isNotNull();
        assertThat(result.getContent().getName()).isNotNull();

        assertThat(result.getLink("self").isPresent()).isTrue();
        assertThat(result.getLink("subcategories").isPresent()).isTrue();
        assertThat(result.getLink("categories").isPresent()).isTrue();
    }

    @Test
    public void testMultipleCategories() {
        CategoryEntity categoryEntity1 = categoryMocker.generateCategoryEntity();
        CategoryEntity categoryEntity2 = categoryMocker.generateCategoryEntity();
        Category category1 = categoryMapper.entityToJson(categoryEntity1);
        Category category2 = categoryMapper.entityToJson(categoryEntity2);

        CollectionModel<EntityModel<Category>> result = categoryAssembler.toCollectionModel(Arrays.asList(category1, category2));
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }

    @Test
    public void testEmptyMultipleCategories() {
        CollectionModel<EntityModel<Category>> result = categoryAssembler.toCollectionModel(new ArrayList<>());
        assertThat(result.getContent().size()).isEqualTo(0);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }
}
