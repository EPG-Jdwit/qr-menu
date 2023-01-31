package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FindByIdTest extends BaseSubcategoryRepositoryTest {

    @Test
    public void notPresentAtStart() {
        assertThat(subcategoryRepository.findById(0L).isEmpty()).isTrue();
    }

    @Test
    public void presentAfterAdd() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findById(subcategoryEntity.getSubcategoryId()).get()).isEqualTo(subcategoryEntity);
    }

    @Test
    public void canAddTwo() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity);
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findById(subcategoryEntity1.getSubcategoryId()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findById(subcategoryEntity2.getSubcategoryId()).get()).isEqualTo(subcategoryEntity2);
        assertThat(subcategoryRepository.findById(0L).isEmpty()).isTrue();
    }

    @Test
    public void canAddToDifferentCategories() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);
        assertThat(subcategoryRepository.findById(subcategoryEntity1.getSubcategoryId()).get()).isEqualTo(subcategoryEntity1);
        assertThat(subcategoryRepository.findById(subcategoryEntity2.getSubcategoryId()).get()).isEqualTo(subcategoryEntity2);
        assertThat(subcategoryRepository.findById(0L).isEmpty()).isTrue();
    }

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategory1();
        assertThat(subcategoryRepository.findById(0L).isEmpty()).isTrue();

        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findById(subcategoryEntity.getSubcategoryId()).get()).isEqualTo(subcategoryEntity);
        subcategoryRepository.deleteById(subcategoryEntity.getSubcategoryId());
        assertThat(subcategoryRepository.findById(subcategoryEntity.getSubcategoryId()).isEmpty()).isTrue();
    }
}
