package com.joshua.qrmenu.category.repository;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FindByNameTest {

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
    public void notPresentAtStart() {
        assertThat(categoryRepository.findByName("Category")).isEmpty();
    }

    @Test
    public void presentAfterAdd() {
        CategoryEntity categoryEntity = createCategoryEntity();
        assertThat(categoryEntity.getCategoryId()).isNotNull();
        assertThat(categoryRepository.findByName(categoryEntity.getName()).get()).isEqualTo(categoryEntity);
        // TODO: isPresent() check
    }

    @Test
    public void notPresentAfterDelete() {
        CategoryEntity categoryEntity = createCategoryEntity();
        assertThat(categoryEntity.getCategoryId()).isNotNull();
        assertThat(categoryRepository.findByName(categoryEntity.getName()).get()).isEqualTo(categoryEntity);
        categoryRepository.deleteById(categoryEntity.getCategoryId());
        assertThat(categoryRepository.findByName(categoryEntity.getName())).isEmpty();
    }

    @Test
    public void canFindMultiple() {
        CategoryEntity categoryEntity1 = createCategoryEntity();
        CategoryEntity categoryEntity2 = createCategoryEntity();
        assertThat(categoryEntity1.getCategoryId()).isNotNull();
        assertThat(categoryEntity2.getCategoryId()).isNotNull();
        CategoryEntity res1 = categoryRepository.findByName(categoryEntity1.getName()).get();
        CategoryEntity res2 = categoryRepository.findByName(categoryEntity2.getName()).get();

        assertThat(res1).isEqualTo(categoryEntity1);
        assertThat(res2).isEqualTo(categoryEntity2);

        categoryRepository.deleteById(categoryEntity1.getCategoryId());

        assertThat(categoryRepository.findByName(categoryEntity1.getName())).isEmpty();
        assertThat(categoryRepository.findByName(categoryEntity2.getName()).get()).isEqualTo(categoryEntity2);
        assertThat(res2).isEqualTo(categoryEntity2);
    }
}
