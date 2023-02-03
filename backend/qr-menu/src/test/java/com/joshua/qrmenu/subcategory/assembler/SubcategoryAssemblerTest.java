package com.joshua.qrmenu.subcategory.assembler;

import com.joshua.qrmenu.endpoints.assemblers.SubcategoryAssembler;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SubcategoryAssemblerTest {

    private static final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    private static final SubcategoryMapper subcategoryMapper = new SubcategoryMapper();

    private static final SubcategoryAssembler subcategoryAssembler = new SubcategoryAssembler();

    @Test
    public void checkSingleSubcategory() {
        SubcategoryEntity subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
        Subcategory subcategory = subcategoryMapper.entityToJson(subcategoryEntity);
        EntityModel<Subcategory> result = subcategoryAssembler.toModel(subcategory);

        // NOTE: This only tests if the generated values from the mocker are returned
        assertThat(result.getContent().getSubcategoryId()).isNotNull();
        assertThat(result.getContent().getName()).isNotNull();
        // Check that jsonIgnore is set
        assertThat(result.getContent().getCategoryId()).isNull();

        assertThat(result.getLink("self").isPresent()).isTrue();
        assertThat(result.getLink("products").isPresent()).isTrue();
        assertThat(result.getLink("subcategories").isPresent()).isTrue();
        assertThat(result.getLink("category").isPresent()).isTrue();
    }

    @Test
    public void checkMultipleSubcategories() {
        SubcategoryEntity subcategoryEntity1 = subcategoryMocker.generateSubcategoryEntity();
        SubcategoryEntity subcategoryEntity2 = subcategoryMocker.generateSubcategoryEntity();
        Subcategory subcategory1 = subcategoryMapper.entityToJson(subcategoryEntity1);
        Subcategory subcategory2 = subcategoryMapper.entityToJson(subcategoryEntity2);

        CollectionModel<EntityModel<Subcategory>> result = subcategoryAssembler.toCollectionModel(Arrays.asList(subcategory1, subcategory2), 1L);

        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }

    @Test
    public void checkEmptyMultipleSubcategories() {
        CollectionModel<EntityModel<Subcategory>> result = subcategoryAssembler.toCollectionModel(new ArrayList<>(), 1L);

        assertThat(result.getContent().size()).isEqualTo(0);
        assertThat(result.getLink("self").isPresent()).isTrue();
    }
}
