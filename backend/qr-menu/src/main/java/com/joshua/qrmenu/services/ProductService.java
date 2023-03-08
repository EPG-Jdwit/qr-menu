package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.ShallowCopy;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.models.validators.Validator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to handle all business logic concerning products.
 */
@Service
public class ProductService extends AbstractService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    /**
     * Constructor for a ProductService
     *
     * @param productRepository : A ProductRepository.
     * @param productMapper : A ProductMapper to map Data Transfer Objects to Entities and back.
     */
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Retrieves all existing Products.
     *
     * @return : A list of all products.
     */
    public List<Product> getAll() {
        Stream<ProductEntity> productEntityStream = productRepository.findAll().stream();

        return productEntityStream
                .map(productMapper::entityToJson)
                .collect(Collectors.toList());

    }

    /**
     * Return a product with a specific ID if found.
     *
     * @param id : ID of the ProductEntity to be retrieved.
     * @return : The product with the given ID.
     * @throws NotFoundException : When the ID doesn't correspond to an existing ProductEntity.
     */
    public Product getProductById(Long id) throws NotFoundException {
        ProductEntity productEntity = parseOptional(productRepository.findById(id));
        return productMapper.entityToJson(productEntity);
    }

    /**
     * Return a product with a specific name if found.
     * @param name : Name of the ProductEntity to be retrieved.
     * @return : The product with the given name.
     * @throws NotFoundException : When no ProductEntity exists with the given name.
     */
    public Product getProductByName(String name) throws NotFoundException {
        ProductEntity productEntity = parseOptional(productRepository.findByName(name));
        return productMapper.entityToJson(productEntity);
    }

    /**
     * Create a new product.
     *
     * @param newProduct : An object with all data to create the new ProductEntity.
     * @return : A Product representing the newly created ProductEntity.
     * @throws InputException : When a required field wasn't provided.
     * @throws AlreadyExistsException : When a ProductEntity already exists with the same name.
     */
    public Product createNewProduct(NewProduct newProduct) throws InputException, AlreadyExistsException {
        Validator validator = new Validator();
        validator.validate(newProduct, ValidatorMode.Create);

        // Check if a product with the same name already exists
        if(productRepository.findByName(newProduct.getName()).isPresent()) {
            throw new AlreadyExistsException("Product with the name '" + newProduct.getName() + "' already exists.");
        }

        ProductEntity productEntity = productMapper.newJsonToEntity(newProduct);
        productEntity = productRepository.save(productEntity);
        return productMapper.entityToJson(productEntity);
    }

    /**
     * Delete a ProductEntity with a specific ID if found.
     *
     * @param id : ID of the ProductEntity to be deleted.
     * @throws NotFoundException : When the ID doesn't correspond to an existing ProductEntity.
     */
    public void deleteProductById(Long id) throws NotFoundException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Update data of an existing ProductEntity with a specific ID if found.
     *
     * @param id : The ID of the ProductEntity to be updated.
     * @param newProduct : An Object containing data to be changed.
     * @return : The Product with the updated fields.
     * @throws NotFoundException : When the ID doesn't correspond to an existing ProductEntity.
     * @throws AlreadyExistsException : When a different ProductEntity already exists with the same name.
     */
    public Product patchProductById(Long id, NewProduct newProduct) throws NotFoundException, AlreadyExistsException {
        ProductEntity originalEntity = parseOptional(productRepository.findById(id));
        ProductEntity newEntity = productMapper.newJsonToEntity(newProduct);

        // Check if another ProductEntity with the same name already exists
        if( newEntity.getName() != null
                && (!newEntity.getName().equals(originalEntity.getName()))
                && productRepository.findByName(newEntity.getName()).isPresent()) {
            throw new AlreadyExistsException("Product with the name '" + newEntity.getName() + "' already exists.");
        }

        newEntity.setSubcategories(originalEntity.getSubcategories());
        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);
        productRepository.save(originalEntity);
        return productMapper.entityToJson(originalEntity);

    }
}
