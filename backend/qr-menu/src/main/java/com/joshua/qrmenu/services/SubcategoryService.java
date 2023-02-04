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
import com.joshua.qrmenu.services.util.AbstractSubcategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubcategoryService extends AbstractSubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryService(SubcategoryRepository subcategoryRepository, ProductRepository productRepository,
                              CategoryRepository categoryRepository, SubcategoryMapper subcategoryMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    public List<Subcategory> getAllCategorySubcategories(Long categoryId) throws NotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException();
        }
        Stream<SubcategoryEntity> subcategoryEntityStream = subcategoryRepository.findAllOfCategory(categoryId).stream();

        return subcategoryEntityStream.map(subcategoryMapper::entityToJson).collect(Collectors.toList());
    }

    public Subcategory getSubcategoryById(Long categoryId, Long subcategoryId) throws NotFoundException {
        SubcategoryEntity subcategoryEntity = parseOptional(subcategoryRepository.findById(subcategoryId));
        verifyCategoryId(categoryId, subcategoryEntity);
        return subcategoryMapper.entityToJson(subcategoryEntity);
    }

    public Subcategory getSubcategoryByName(Long categoryId, String name) throws NotFoundException {
        SubcategoryEntity subcategoryEntity = parseOptional(subcategoryRepository.findByName(name, categoryId));
        verifyCategoryId(categoryId, subcategoryEntity);
        return subcategoryMapper.entityToJson(subcategoryEntity);
    }
    public Subcategory createNewSubcategory(Long categoryId, NewSubcategory newSubcategory) throws NotFoundException, InputException, AlreadyExistsException {
        // Check if the category exists, otherwise throw a NotFoundException
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(categoryId));
        // Validate the input
        Validator validator = new Validator();
        validator.validate(newSubcategory, ValidatorMode.Create);
        // Check if a category with the same name already exists
        if(subcategoryRepository.findByName(newSubcategory.getName(), categoryId).isPresent()) {
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

    public void deleteSubcategoryById(Long categoryId, Long subcategoryId) throws NotFoundException {
        if (subcategoryRepository.existsById(subcategoryId)) {
            SubcategoryEntity subcategoryEntity = parseOptional(subcategoryRepository.findById(subcategoryId));
            verifyCategoryId(categoryId, subcategoryEntity);
            subcategoryRepository.deleteById(subcategoryId);
        } else {
            throw new NotFoundException();
        }
    }

    public Subcategory patchSubcategoryById(Long categoryId, Long subcategoryId, NewSubcategory newSubcategory) throws NotFoundException, AlreadyExistsException {
        // Check if the category exists, otherwise throw a NotFoundException
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(categoryId));
        SubcategoryEntity originalEntity = parseOptional(subcategoryRepository.findById(subcategoryId));
        verifyCategoryId(categoryId, originalEntity);
        SubcategoryEntity newEntity = subcategoryMapper.newJsonToEntity(newSubcategory);
        // Check if another subcategory (within the same category) with the same name already exists
        if( newEntity.getName() != null
                && (!newEntity.getName().equals(originalEntity.getName()))
                && subcategoryRepository.findByName(newEntity.getName(), categoryId).isPresent()) {
            throw new AlreadyExistsException("Subcategory (within the category '" + categoryEntity.getName() +"') with the name '" + newEntity.getName() + "' already exists.");
        }
        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);

        addProductsToSubcategory(newSubcategory, originalEntity);
        subcategoryRepository.save(originalEntity);
        return subcategoryMapper.entityToJson(originalEntity);
    }

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
