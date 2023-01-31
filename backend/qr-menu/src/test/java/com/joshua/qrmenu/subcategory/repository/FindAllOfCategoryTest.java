package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FindAllOfCategoryTest extends BaseSubcategoryRepositoryTest {

    @Test
    public void emptyCategoryAtStart() {
        CategoryEntity categoryEntity = createCategory1();
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).isEmpty()).isTrue();
    }

    @Test
    public void canFindOne() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).size()).isEqualTo(1);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).contains(subcategoryEntity)).isTrue();
    }

    @Test
    public void notFoundInOtherCategory() {
        CategoryEntity categoryEntity1 = createCategory1();
        CategoryEntity categoryEntity2 = createCategory2();
        createSubcategoryEntity(categoryEntity1);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity2.getCategoryId()).isEmpty()).isTrue();
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity2.getCategoryId()).size()).isEqualTo(0);
    }

    @Test
    public void canFindTwo() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity);
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).size()).isEqualTo(2);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).containsAll(Arrays.asList(subcategoryEntity1, subcategoryEntity2))).isTrue();
    }

    @Test
    public void canFindTen() {
        CategoryEntity categoryEntity = createCategory1();
        for (int i = 0; i < 10; i++) {
            createSubcategoryEntity(categoryEntity);
        }
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).size()).isEqualTo(10);
    }

    @Test
    public void canAddToDifferentCategories() {
        CategoryEntity categoryEntity1 = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity1);
        CategoryEntity categoryEntity2 = createCategory1();
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity2);

        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity1.getCategoryId()).size()).isEqualTo(1);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity1.getCategoryId()).contains(subcategoryEntity1)).isTrue();
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity2.getCategoryId()).size()).isEqualTo(1);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity2.getCategoryId()).contains(subcategoryEntity2)).isTrue();
    }

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategory1();

        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).size()).isEqualTo(0);

        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).size()).isEqualTo(1);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).contains(subcategoryEntity)).isTrue();

        subcategoryRepository.deleteById(subcategoryEntity.getSubcategoryId());

        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).size()).isEqualTo(0);
        assertThat(subcategoryRepository.findAllOfCategory(categoryEntity.getCategoryId()).contains(subcategoryEntity)).isFalse();

        // Extra check for now but should be done somewhere else
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().contains(categoryEntity)).isTrue();
    }
}
