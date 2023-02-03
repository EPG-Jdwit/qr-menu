package com.joshua.qrmenu.subcategory.mapper;

import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubcategoryMapperTest {

    private static final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    private static final SubcategoryMapper subcategoryMapper = new SubcategoryMapper();

    private void checkEquality(SubcategoryEntity subcategoryEntity, Subcategory subcategory) {
        assertThat(subcategoryEntity.getSubcategoryId()).isEqualTo(subcategory.getSubcategoryId());
        assertThat(subcategoryEntity.getName()).isEqualTo(subcategory.getName());
    }

    private void checkEquality(NewSubcategory newSubcategory, SubcategoryEntity subcategoryEntity) {
        assertThat(newSubcategory.getName()).isEqualTo(subcategoryEntity.getName());
    }

    @Test
    public void checkEntityToSubcategory() {
        SubcategoryEntity subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
        Subcategory subcategory = subcategoryMapper.entityToJson(subcategoryEntity);
        checkEquality(subcategoryEntity, subcategory);
    }

    @Test
    public void checkEntityToSubcategoryPartialFields() {
        SubcategoryEntity subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
        subcategoryEntity.setName(null);
        Subcategory subcategory = subcategoryMapper.entityToJson(subcategoryEntity);
        checkEquality(subcategoryEntity, subcategory);
    }

    @Test
    public void checkNewSubcategoryToEntity() {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        SubcategoryEntity subcategoryEntity = subcategoryMapper.newJsonToEntity(newSubcategory);
        checkEquality(newSubcategory, subcategoryEntity);
    }

    @Test
    public void checkNewSubcategoryToEntityPartialFields() {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setName(null);
        SubcategoryEntity subcategoryEntity = subcategoryMapper.newJsonToEntity(newSubcategory);
        checkEquality(newSubcategory, subcategoryEntity);
    }
}
