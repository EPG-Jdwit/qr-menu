package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to handle all business logic concerning categories.
 */
@Service
public class CategoryService extends AbstractService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    /**
     * Constructor for a CategoryService.
     * @param categoryRepository: A CategoryRepository.
     * @param categoryMapper: A CategoryMapper to map Data Transfer Objects to Entities and back.
     */
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Retrieves all existing Categories
     * @return : A List of all Categories.
     */
    public List<Category> getAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderNr"));

        return categoryEntityList
                .stream()
                .map(categoryMapper::entityToJson)
                .collect(Collectors.toList());
    }

    /**
     * Return a category with a specific ID if found.
     * @param id : ID of the CategoryEntity to be retrieved.
     * @return : The category with the given ID,
     * @throws NotFoundException : When the ID doesn't correspond to an existing CategoryEntity.
     */
    public Category getCategoryById(Long id) throws NotFoundException {
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(id));
        return categoryMapper.entityToJson(categoryEntity);
    }

    /**
     * Return a category with a specific name if found.
     * @param name : Name of the CategoryEntity to be retrieved.
     * @return : The category with the given name,
     * @throws NotFoundException : When no CategoryEntity exists with the given name.
     */
    public Category getCategoryByName(String name) throws NotFoundException {
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findByName(name));
        return categoryMapper.entityToJson(categoryEntity);
    }

    /**
     * Create a new category.
     * @param newCategory : An object with all data to create the new CategoryEntity.
     * @return : A Category representing the newly created CategoryEntity.
     * @throws InputException : When a required field wasn't provided.
     * @throws AlreadyExistsException : When a CategoryEntity already exists with the same name.
     */
    public Category createNewCategory(NewCategory newCategory) throws InputException, AlreadyExistsException {
        // Validate input
        Validator validator = new Validator();
        validator.validate(newCategory, ValidatorMode.Create);

        // Check if a category with the same name already exists
        if(categoryRepository.findByName(newCategory.getName()).isPresent()) {
            throw new AlreadyExistsException("Category with the name '" + newCategory.getName() + "' already exists.");
        }

        CategoryEntity categoryEntity = categoryMapper.newJsonToEntity(newCategory);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.entityToJson(categoryEntity);
    }

    /**
     * Delete a CategoryEntity with a specific ID if found.
     *
     * @param id : ID of the CategoryEntity to be deleted.
     * @throws NotFoundException : When the ID doesn't correspond to an existing CategoryEntity.
     */
    public void deleteCategoryById(Long id) throws NotFoundException {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Update data of an existing CategoryEntity with a specific ID if found.
     *
     * @param id : The ID of the CategoryEntity to be updated.
     * @param newCategory : An Object containing data to be changed.
     * @return : The Category with the updated fields.
     * @throws NotFoundException : When the ID doesn't correspond to an existing CategoryEntity.
     * @throws AlreadyExistsException : When a different CategoryEntity already exists with the same name.
     */
    public Category patchCategoryById(Long id, NewCategory newCategory) throws NotFoundException, AlreadyExistsException {
        CategoryEntity originalEntity = parseOptional(categoryRepository.findById(id));
        CategoryEntity newEntity = categoryMapper.newJsonToEntity(newCategory);

        // Check if another CategoryEntity with the same name already exists
        if( newEntity.getName() != null
                && (!newEntity.getName().equals(originalEntity.getName()))
                && categoryRepository.findByName(newCategory.getName()).isPresent()) {
            throw new AlreadyExistsException("Category with the name '" + newCategory.getName() + "' already exists.");
        }

        newEntity.setSubcategoryEntities(originalEntity.getSubcategoryEntities());

        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);

        categoryRepository.save(originalEntity);
        return categoryMapper.entityToJson(originalEntity);
    }
}
