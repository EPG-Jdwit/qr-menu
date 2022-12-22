package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.ShallowCopy;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // No products when creating
    public Category createNewCategory(NewCategory newCategory) {
        CategoryEntity categoryEntity = categoryMapper.newJsonToEntity(newCategory);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.entityToJson(categoryEntity);
    }

    public void deleteCategoryById(Long id) throws NotFoundException {
        categoryRepository.deleteById(id);
    }

    public Category patchCategoryById(Long id, NewCategory newCategory) throws NotFoundException {
        CategoryEntity originalEntity = parseOptional(categoryRepository.findById(id));
        CategoryEntity newEntity = categoryMapper.newJsonToEntity(newCategory);

        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);

        addProductsToCategory(newCategory, originalEntity);
        categoryRepository.save(originalEntity);
        return categoryMapper.entityToJson(originalEntity);
    }

    private void addProductsToCategory(NewCategory newCategory, CategoryEntity categoryEntity) throws NotFoundException {
        // Add products
        if (newCategory.getProducts() != null) {
            Set<ProductEntity> categoryProductEntities = new HashSet<>();
            for (Long productId : newCategory.getProducts()) {
                // Todo use ProductService
                ProductEntity productEntity = parseOptional(productRepository.findById(productId));
                categoryProductEntities.add(productEntity);
            }
            categoryEntity.setProducts(categoryProductEntities);
        }
    }
}
