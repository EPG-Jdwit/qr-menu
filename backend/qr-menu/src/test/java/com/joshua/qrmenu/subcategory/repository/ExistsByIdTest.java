package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ExistsByIdTest extends BaseSubcategoryRepositoryTest {

    @Test
    public void notPresentAtStart() {
        assertThat(subcategoryRepository.existsById(0L)).isFalse();
    }

    @Test
    public void notPresentAfterCategoryAdded() {
        createCategory1();
        assertThat(subcategoryRepository.existsById(0L)).isFalse();
    }
    @Test
    public void canAddOne() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.existsById(subcategoryEntity.getSubcategoryId())).isTrue();
    }

    @Test
    public void canAddTwo() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity1 = createSubcategoryEntity(categoryEntity);
        SubcategoryEntity subcategoryEntity2 = createSubcategoryEntity(categoryEntity);

        assertThat(subcategoryRepository.existsById(subcategoryEntity1.getSubcategoryId())).isTrue();
        assertThat(subcategoryRepository.existsById(subcategoryEntity2.getSubcategoryId())).isTrue();
    }

    @Test
    public void noLongerExistsAfterDelete() {
        CategoryEntity categoryEntity = createCategory1();
        SubcategoryEntity subcategoryEntity = createSubcategoryEntity(categoryEntity);
        assertThat(subcategoryRepository.existsById(subcategoryEntity.getSubcategoryId())).isTrue();
        subcategoryRepository.deleteById(subcategoryEntity.getSubcategoryId());
        assertThat(subcategoryRepository.existsById(subcategoryEntity.getSubcategoryId())).isFalse();
    }
}
