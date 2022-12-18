package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.ProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;

    public ProductController(ProductService productService, ProductAssembler productAssembler) {
        this.productService = productService;
        this.productAssembler = productAssembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> getAllProducts() {
        return productAssembler.toCollectionModel(
                productService.getAll()
        );
    }

    @PostMapping("/products")
    public EntityModel<Product> addProduct(@RequestBody NewProduct newProduct) {
        return productAssembler.toModel(productService.createNewProduct(newProduct));
    }

}
