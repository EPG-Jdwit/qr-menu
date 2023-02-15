package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.endpoints.assemblers.SubProductAssembler;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.SubProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints to get products from categories
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SubProductController extends BaseController {

        private final SubProductService subProductService;
        private final SubProductAssembler subProductAssembler;

    /**
     * Constructor.
     *
     * @param subProductService : A SubProductService.
     * @param subProductAssembler : A SubProductAssembler to create EntityModels of products with added hateaos links.
     */
    public SubProductController(SubProductService subProductService, SubProductAssembler subProductAssembler) {
            this.subProductService = subProductService;
            this.subProductAssembler = subProductAssembler;
    }

    /**
     * Retrieves all the products of the subcategory of the category with provided ID's.
     *
     * @param categoryId : The ID of the category of which the subcategorie's products are to be retrieved.
     * @param subcategoryId : The ID of the subcategory of which the products are to be retrieved.
     * @return : A CollectionModel of the products of the subcategory.
     * @throws NotFoundException : When either a category or subcategory which provided IDs aren't found.
     */
    @GetMapping("/categories/{categoryId}/subcategories/{subcategoryId}/products")
    public CollectionModel<EntityModel<Product>> getSubcategoryProducts(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
        return subProductAssembler.toCollectionModel(subProductService.getSubcategoryProducts(categoryId, subcategoryId), categoryId, subcategoryId);
    }
}
