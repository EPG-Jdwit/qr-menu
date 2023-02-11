package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindByNameTest extends BaseSubcategoryRepositoryTest {

    @Test
    public void notPresentAtStart() {
        assertThat(subcategoryRepository.findByName(0L, "Subcategory").isEmpty()).isTrue();
    }

    @Test
    public void presentAfterAdd() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findByName(categoryEntity.getCategoryId(), subcategoryEntity.getName()).get()).isEqualTo(subcategoryEntity);
    }

    @Test
    public void canAddTwo() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity);
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findByName(categoryEntity.getCategoryId(), subcategoryEntity1.getName()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findByName(categoryEntity.getCategoryId(), subcategoryEntity2.getName()).get()).isEqualTo(subcategoryEntity2);
    }

    @Test
    public void canAddToDifferentCategories() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);
        assertThat(subcategoryRepository.findByName(categoryEntity1.getCategoryId(), subcategoryEntity1.getName()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findByName(categoryEntity2.getCategoryId(), subcategoryEntity2.getName()).get()).isEqualTo(subcategoryEntity2);
    }

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategory1();

        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findByName(categoryEntity.getCategoryId(), subcategoryEntity.getName()).get()).isEqualTo(subcategoryEntity);
        subcategoryRepository.deleteById(subcategoryEntity.getSubcategoryId());
        assertThat(subcategoryRepository.findByName(categoryEntity.getCategoryId(), subcategoryEntity.getName()).isEmpty()).isTrue();
    }

    @Test
    public void addToDifferentCategoriesSameName() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);
        subcategoryEntity2.setName(categoryEntity1.getName());
        assertThat(subcategoryRepository.findByName(categoryEntity1.getCategoryId(), subcategoryEntity1.getName()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findByName(categoryEntity2.getCategoryId(), subcategoryEntity2.getName()).get()).isEqualTo(subcategoryEntity2);
    }
}
