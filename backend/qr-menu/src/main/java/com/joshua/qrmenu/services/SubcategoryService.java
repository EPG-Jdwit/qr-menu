package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.ShallowCopy;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.models.validators.Validator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.repositories.SubcategoryRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to handle all business logic concerning subcategories.
 */
@Service
public class SubcategoryService extends AbstractService {

    private final SubcategoryRepository subcategoryRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final SubcategoryMapper subcategoryMapper;

    /**
     * Constructor
     *
     * @param subcategoryRepository: A SubcategoryRepository.
     * @param productRepository: A ProductRepository.
     * @param categoryRepository: A CategoryRepository.
     * @param subcategoryMapper: A SubcategoryMapper to map Data Transfer Objects to Entities and back.
     */
    public SubcategoryService(SubcategoryRepository subcategoryRepository, ProductRepository productRepository,
                              CategoryRepository categoryRepository, SubcategoryMapper subcategoryMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    /**
     * Retrieves all existing Subcategories of a CategoryEntity with given ID.
     * @param categoryId : The ID of the CategoryEntity to which the Subcategories belong.
     * @return : A List of all the subcategories of the CategoryEntity.
     * @throws NotFoundException : When the ID doesn't correspond to an existing CategoryEntity.
     */
    public List<Subcategory> getAllCategorySubcategories(Long categoryId) throws NotFoundException {
        // Check if a CategoryEntity exists with an ID equal to CategoryID
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException();
        }
        List<SubcategoryEntity> subcategoryEntityList = subcategoryRepository.findAllOfCategory(categoryId, Sort.by(Sort.Direction.ASC, "orderNr"));

        return subcategoryEntityList.stream().map(subcategoryMapper::entityToJson).collect(Collectors.toList());
    }

    /**
     * Return all existing subcategories
     * @return : A List of all existing subcategories
     */
    public List<Subcategory> getAll() {
        List<SubcategoryEntity> subcategoryEntityList = subcategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderNr"));
        return subcategoryEntityList.stream().map(subcategoryMapper::entityToJson).collect(Collectors.toList());
    }

    /**
     * Returns a subcategory of a category with specific ID's if found.
     *
     * @param categoryId : ID of the CategoryEntity that owns the Subcategory.
     * @param subcategoryId : ID of the SubcategoryEntity to be retrieved,
     * @return : The subcategory with the given ID.
     * @throws NotFoundException : When either the CategoryEntity or SubcategoryEntity with given IDs weren't found.
     */
    public Subcategory getSubcategoryById(Long categoryId, Long subcategoryId) throws NotFoundException {
        SubcategoryEntity subcategoryEntity = parseOptional(subcategoryRepository.findById(categoryId, subcategoryId));
        return subcategoryMapper.entityToJson(subcategoryEntity);
    }

    /**
     * Return a subcategory (of a category with given ID) with a specific name if found.
     * @param categoryId : ID of the CategoryEntity that owns the Subcategory.
     * @param name : Name of the SubcategoryEntity to be retrieved.
     * @return : The subcategory with the given name.
     * @throws NotFoundException : When no SubcategoryEntity exists with the given name or Category with given ID.
     */
    public Subcategory getSubcategoryByName(Long categoryId, String name) throws NotFoundException {
        SubcategoryEntity subcategoryEntity = parseOptional(subcategoryRepository.findByName(categoryId, name));
        return subcategoryMapper.entityToJson(subcategoryEntity);
    }

    /**
     * Create a new Subcategory.
     *
     * @param categoryId : ID of the CategoryEntity that owns the Subcategory.
     * @param newSubcategory : An object with all data to create the new SubcategoryEntity.
     * @return : A Subcategory representing the newly created SubcategoryEntity.
     * @throws NotFoundException : When the ID doesn't correspond to an existing CategoryEntity.
     * @throws InputException : When a required field wasn't provided.
     * @throws AlreadyExistsException : When a SubcategoryEntity already exists with the same name.
     */
    public Subcategory createNewSubcategory(Long categoryId, NewSubcategory newSubcategory) throws NotFoundException, InputException, AlreadyExistsException {
        // Check if the category exists, otherwise throw a NotFoundException
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(categoryId));
        // Validate the input
        Validator validator = new Validator();
        validator.validate(newSubcategory, ValidatorMode.Create);
        // Check if a category with the same name already exists
        if(subcategoryRepository.findByName(categoryId, newSubcategory.getName()).isPresent()) {
            throw new AlreadyExistsException("Subcategory with the name '" + newSubcategory.getName() + "' already exists.");
        }
        // Map the Subcategory to SubcategoryEntity
        SubcategoryEntity subcategoryEntity = subcategoryMapper.newJsonToEntity(newSubcategory);

        // Set the CategoryEntity of the SubcategoryEntity
        subcategoryEntity.setCategoryEntity(categoryEntity);
        // Add the SubcategoryEntity to the CategoryEntity's SubcategoryEntities
        List<SubcategoryEntity> tmp = categoryEntity.getSubcategoryEntities();
        tmp.add(subcategoryEntity);
        categoryEntity.setSubcategoryEntities(tmp);

        addProductsToSubcategory(newSubcategory, subcategoryEntity);
        // Save the SubcategoryEntity and return a Subcategory object
        subcategoryEntity = subcategoryRepository.save(subcategoryEntity);
        return subcategoryMapper.entityToJson(subcategoryEntity);
    }

    /**
     * Delete a SubcategoryEntity of a CategoryEntity with specific IDs.
     *
     * @param categoryId : ID of the CategoryEntity that owns the SubcategoryEntity.
     * @param subcategoryId : ID of the SubcategoryEntity to be deleted.
     * @throws NotFoundException : When either the CategoryEntity or SubcategoryEntity with given IDs weren't found.
     */
    public void deleteSubcategoryById(Long categoryId, Long subcategoryId) throws NotFoundException {
        // Check if the subcategory can be found for the category
        parseOptional(subcategoryRepository.findById(categoryId, subcategoryId));
        // Delete the subcategory
        subcategoryRepository.deleteById(categoryId, subcategoryId);
    }

    /**
     * Update data of an existing SubcategoryEntity of a CategoryEntity with specific IDs if found.
     *
     * @param categoryId : ID of the CategoryEntity that owns the SubcategoryEntity.
     * @param subcategoryId : ID of the SubcategoryEntity to be updated.
     * @param newSubcategory : An Object containing data to be changed.
     * @return : The Subcategory with updated fields.
     * @throws NotFoundException : When either the CategoryEntity or SubcategoryEntity with given IDs weren't found.
     * @throws AlreadyExistsException : When a SubcategoryEntity already exists with the same name.
     */
    public Subcategory patchSubcategoryById(Long categoryId, Long subcategoryId, NewSubcategory newSubcategory) throws NotFoundException, AlreadyExistsException {
        // Check if the category exists, otherwise throw a NotFoundException
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(categoryId));
        SubcategoryEntity originalEntity = parseOptional(subcategoryRepository.findById(categoryId, subcategoryId));
        SubcategoryEntity newEntity = subcategoryMapper.newJsonToEntity(newSubcategory);
        // Check if another subcategory (within the same category) with the same name already exists
        if( newEntity.getName() != null
                && (!newEntity.getName().equals(originalEntity.getName()))
                && subcategoryRepository.findByName(categoryId, newEntity.getName()).isPresent()) {
            throw new AlreadyExistsException("Subcategory (within the category '" + categoryEntity.getName() +"') with the name '" + newEntity.getName() + "' already exists.");
        }
        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);

        addProductsToSubcategory(newSubcategory, originalEntity);
        subcategoryRepository.save(originalEntity);
        return subcategoryMapper.entityToJson(originalEntity);
    }

    /**
     * Adds all ProductEntities with ID's in the NewSubcategory to the SubcategoryEntity.
     *
     * @param newSubcategory : A NewSubcategory containing a list of product ID's.
     * @param subcategoryEntity : The SubcategoryEntity to which the products should be added.
     * @throws NotFoundException: When the ID doesn't correspond to an existing ProductEntity.
     */
    private void addProductsToSubcategory(NewSubcategory newSubcategory, SubcategoryEntity subcategoryEntity) throws NotFoundException {
        // Add products
        if (newSubcategory.getProducts() != null) {
            List<ProductEntity> subcategoryProductEntities = new ArrayList<>();
            for (Long productId : newSubcategory.getProducts()) {
                // Todo use ProductService
                ProductEntity productEntity = parseOptional(productRepository.findById(productId));
                subcategoryProductEntities.add(productEntity);
            }
            subcategoryEntity.setProducts(subcategoryProductEntities);
        }
    }
}
