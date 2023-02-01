package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.SubProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubProductController extends BaseController {

        private final SubProductService subProductService;
        private final ProductAssembler productAssembler;

        public SubProductController(SubProductService subProductService, ProductAssembler productAssembler) {
            this.subProductService = subProductService;
            this.productAssembler = productAssembler;
        }

        @GetMapping("/categories/{categoryId}/subcategories/{subcategoryId}/products")
        public CollectionModel<EntityModel<Product>> getSubcategoryProducts(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
            return productAssembler.toCollectionModel(subProductService.getSubcategoryProducts(categoryId, subcategoryId));
        }
}
