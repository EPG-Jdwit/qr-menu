package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.ProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Endpoints for products
 */
@RestController
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;

    /**
     * Constructor.
     *
     * @param productService : A ProductService.
     * @param productAssembler : A ProductAssembler to create EntityModels of Products.
     */
    public ProductController(ProductService productService, ProductAssembler productAssembler) {
        this.productService = productService;
        this.productAssembler = productAssembler;
    }

    /**
     * Retrieve all products.
     *
     * @return: A CollectionModel of the list of all existing products.
     */
    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> getAllProducts() {
        return productAssembler.toCollectionModel(
                productService.getAll()
        );
    }

    /**
     * Retrieve a product with a specific ID.
     *
     * @param productId : The ID of the product to be retrieved.
     * @return : A product with the given ID if found.
     * @throws NotFoundException : When the ID doesn't correspond to an existing product.
     */
    @GetMapping("/products/{productId}")
    public EntityModel<Product> getProductById(@PathVariable Long productId) throws NotFoundException {
        return productAssembler.toModel(productService.getProductById(productId));
    }

    /**
     * Creates a new product.
     *
     * @param newProduct : An Object with data to create a new product.
     * @return : An EntityModel of the added product.
     */
    @PostMapping("/products")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityModel<Product> addProduct(@RequestBody NewProduct newProduct) throws InputException, AlreadyExistsException {
        return productAssembler.toModel(productService.createNewProduct(newProduct));
    }

    /**
     * Delete a product with a specific ID.
     *
     * @param productId : ID of the product to be deleted.
     * @throws NotFoundException : When the ID doesn't correspond to an existing product.
     */
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long productId) throws NotFoundException {
        productService.deleteProductById(productId);
    }

    /**
     * Update the data of a product with a specific ID. Fields that weren't changed remain the same.
     *
     * @param productId : ID of the product to be updated.
     * @param newProduct : An Object with data of the product to be changed.
     * @return : An EntityModel of the changed product.
     * @throws NotFoundException : When the ID doesn't correspond to an existing product.
     */
    @PatchMapping("/products/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Product> updateProductById(@PathVariable Long productId, @RequestBody NewProduct newProduct) throws NotFoundException, AlreadyExistsException {
        return productAssembler.toModel(productService.patchProductById(productId, newProduct));
    }
}
