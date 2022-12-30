package com.joshua.qrmenu.category.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FindAllTest {

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
    public void emptyAtStart() {
        assertThat(categoryRepository.findAll().isEmpty()).isTrue();
        assertThat(categoryRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void canAddOne() {
        CategoryEntity categoryEntity = createCategoryEntity();
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().contains(categoryEntity)).isTrue();
    }

    @Test
    public void canAddTwo() {
        CategoryEntity categoryEntity1 = createCategoryEntity();
        CategoryEntity categoryEntity2 = createCategoryEntity();

        assertThat(categoryRepository.findAll().size()).isEqualTo(2);
        assertThat(categoryRepository.findAll().containsAll(Arrays.asList(categoryEntity1, categoryEntity2))).isTrue();
    }

    @Test
    public void canAddTen() {
        for (int i = 0; i < 10; i++) {
            createCategoryEntity();
        }
        assertThat(categoryRepository.findAll().size()).isEqualTo(10);
    }

    //TODO: make own test suite
    @Test
    public void notPresentAfterDelete() {
        assertThat(categoryRepository.findAll().size()).isEqualTo(0);
        CategoryEntity categoryEntity = createCategoryEntity();
        assertThat(categoryEntity.getCategoryId()).isNotNull();
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().contains(categoryEntity)).isTrue();
        categoryRepository.deleteById(categoryEntity.getCategoryId());
        assertThat(categoryRepository.findAll().contains(categoryEntity)).isFalse();
        assertThat(categoryRepository.findAll().size()).isEqualTo(0);

    }
}
