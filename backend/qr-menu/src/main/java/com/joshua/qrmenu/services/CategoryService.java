package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.ShallowCopy;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.models.validators.Validator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryService extends AbstractService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<Category> getAll() {
        Stream<CategoryEntity> categoryEntityStream = categoryRepository.findAll().stream();

        return categoryEntityStream
                .map(categoryMapper::entityToJson)
                .collect(Collectors.toList());
    }

    public Category getCategoryById(Long id) throws NotFoundException {
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(id));
        return categoryMapper.entityToJson(categoryEntity);
    }

    public Category getCategoryByName(String name) throws NotFoundException {
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findByName(name));
        return categoryMapper.entityToJson(categoryEntity);
    }

    // No products when creating
    public Category createNewCategory(NewCategory newCategory) throws InputException {
        // Validate input
        Validator validator = new Validator();
        validator.validate(newCategory, ValidatorMode.Create);

        CategoryEntity categoryEntity = categoryMapper.newJsonToEntity(newCategory);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.entityToJson(categoryEntity);
    }

    public void deleteCategoryById(Long id) throws NotFoundException {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    public Category patchCategoryById(Long id, NewCategory newCategory) throws NotFoundException {
        CategoryEntity originalEntity = parseOptional(categoryRepository.findById(id));
        CategoryEntity newEntity = categoryMapper.newJsonToEntity(newCategory);
        // TODO: Change this if allow NewCategory to contain subcategories in the future
        newEntity.setSubcategoryEntities(originalEntity.getSubcategoryEntities());

        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);

        categoryRepository.save(originalEntity);
        return categoryMapper.entityToJson(originalEntity);
    }
}
