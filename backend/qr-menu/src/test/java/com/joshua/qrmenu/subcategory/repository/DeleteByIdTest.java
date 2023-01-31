package com.joshua.qrmenu.subcategory.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class DeleteByIdTest extends BaseSubcategoryRepositoryTest {

    // TODO: This is duplication of FindAllTest
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
