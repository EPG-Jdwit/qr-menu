package com.joshua.qrmenu.category.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FindByIdTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryMocker categoryMocker = new CategoryMocker();

    private CategoryEntity createCategoryEntity() {
        CategoryEntity categoryEntity = categoryMocker.generateCategoryEntity();
        categoryEntity.setCategoryId(null);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntity;
    }

    @Test
    public void presentAfterAdd() {
        CategoryEntity categoryEntity = createCategoryEntity();
        assertThat(categoryEntity.getCategoryId()).isNotNull();
        assertThat(categoryRepository.existsById(categoryEntity.getCategoryId())).isTrue();
        // TODO: isPresent check
        assertThat(categoryRepository.findById(categoryEntity.getCategoryId()).get()).isEqualTo(categoryEntity);
    }

    // TODO: Use findById instead of exists in following

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategoryEntity();
        assertThat(categoryEntity.getCategoryId()).isNotNull();
        assertThat(categoryRepository.existsById(categoryEntity.getCategoryId())).isTrue();
        categoryRepository.deleteById(categoryEntity.getCategoryId());
        assertThat(categoryRepository.existsById(categoryEntity.getCategoryId())).isFalse();
    }

    @Test
    public void canFindMultiple() {
        CategoryEntity categoryEntity1 = createCategoryEntity();
        CategoryEntity categoryEntity2 = createCategoryEntity();
        assertThat(categoryEntity1.getCategoryId()).isNotNull();
        assertThat(categoryEntity2.getCategoryId()).isNotNull();

        assertThat(categoryRepository.existsById(categoryEntity1.getCategoryId())).isTrue();
        assertThat(categoryRepository.existsById(categoryEntity2.getCategoryId())).isTrue();

        categoryRepository.deleteById(categoryEntity1.getCategoryId());
        assertThat(categoryRepository.existsById(categoryEntity1.getCategoryId())).isFalse();
        assertThat(categoryRepository.existsById(categoryEntity2.getCategoryId())).isTrue();
    }
}
