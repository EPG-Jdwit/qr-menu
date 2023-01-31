package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindAllTest extends BaseSubcategoryRepositoryTest {

    @Test
    public void emptyAtStart() {
        assertThat(subcategoryRepository.findAll().isEmpty()).isTrue();
        assertThat(subcategoryRepository.findAll().size()).isEqualTo(0);

        // Unneeded but can't hurt to check
        assertThat(categoryRepository.findAll().isEmpty()).isTrue();
        assertThat(categoryRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void emptyAfterCategoryCreated() {
        createCategory1();
        assertThat(subcategoryRepository.findAll().isEmpty()).isTrue();
        assertThat(subcategoryRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void canAddOne() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findAll().size()).isEqualTo(1);
        assertThat(subcategoryRepository.findAll().contains(subcategoryEntity)).isTrue();
    }

    @Test
    public void canAddTwo() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity);
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity);

        assertThat(subcategoryRepository.findAll().size()).isEqualTo(2);
        assertThat(subcategoryRepository.findAll().containsAll(Arrays.asList(subcategoryEntity1, subcategoryEntity2))).isTrue();
    }

    @Test
    public void canAddTen() {
        CategoryEntity categoryEntity = createCategory1();
        for (int i = 0; i < 10; i++) {
            createSubcategoryEntity(categoryEntity);
        }
        assertThat(subcategoryRepository.findAll().size()).isEqualTo(10);
    }

    @Test
    public void canAddToDifferentCategories() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);

        assertThat(subcategoryRepository.findAll().size()).isEqualTo(2);
        assertThat(subcategoryRepository.findAll().containsAll(Arrays.asList(subcategoryEntity1, subcategoryEntity2))).isTrue();
    }

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategory1();

        assertThat(subcategoryRepository.findAll().size()).isEqualTo(0);

        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findAll().size()).isEqualTo(1);
        assertThat(subcategoryRepository.findAll().contains(subcategoryEntity)).isTrue();

        subcategoryRepository.deleteById(subcategoryEntity.getSubcategoryId());

        assertThat(subcategoryRepository.findAll().size()).isEqualTo(0);
        assertThat(subcategoryRepository.findAll().contains(subcategoryEntity)).isFalse();

        // Extra check for now but should be done somewhere else
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().contains(categoryEntity)).isTrue();
    }

}
