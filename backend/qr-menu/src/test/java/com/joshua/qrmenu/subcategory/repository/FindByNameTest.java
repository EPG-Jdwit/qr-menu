package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindByNameTest extends BaseSubcategoryRepositoryTest {

    @Test
    public void notPresentAtStart() {
        assertThat(subcategoryRepository.findByName("Subcategory", 0L).isEmpty()).isTrue();
    }

    @Test
    public void presentAfterAdd() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findByName(subcategoryEntity.getName(), categoryEntity.getCategoryId()).get()).isEqualTo(subcategoryEntity);
    }

    @Test
    public void canAddTwo() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity);
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findByName(subcategoryEntity1.getName(), categoryEntity.getCategoryId()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findByName(subcategoryEntity2.getName(), categoryEntity.getCategoryId()).get()).isEqualTo(subcategoryEntity2);
    }

    @Test
    public void canAddToDifferentCategories() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);
        assertThat(subcategoryRepository.findByName(subcategoryEntity1.getName(), categoryEntity1.getCategoryId()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findByName(subcategoryEntity2.getName(), categoryEntity2.getCategoryId()).get()).isEqualTo(subcategoryEntity2);
    }

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategory1();

        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findByName(subcategoryEntity.getName(), categoryEntity.getCategoryId()).get()).isEqualTo(subcategoryEntity);
        subcategoryRepository.deleteById(subcategoryEntity.getSubcategoryId());
        assertThat(subcategoryRepository.findByName(subcategoryEntity.getName(), categoryEntity.getCategoryId()).isEmpty()).isTrue();
    }

    @Test
    public void addToDifferentCategoriesSameName() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);
        subcategoryEntity2.setName(categoryEntity1.getName());
        assertThat(subcategoryRepository.findByName(subcategoryEntity1.getName(), categoryEntity1.getCategoryId()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findByName(subcategoryEntity2.getName(), categoryEntity2.getCategoryId()).get()).isEqualTo(subcategoryEntity2);
    }
}
