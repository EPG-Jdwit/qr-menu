package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.endpoints.CategoryController;
import com.joshua.qrmenu.endpoints.ProductController;
import com.joshua.qrmenu.endpoints.SubProductController;
import com.joshua.qrmenu.endpoints.SubcategoryController;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SubProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    // TODO: Not used due to needing categoryId and subcategoryId, find a way to extract out of Iterable
    @Override
    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> subcategories) {
        return CollectionModel.of(
                StreamSupport.stream(subcategories.spliterator(), false).map(this::toModel).collect(Collectors.toList())
        );
    }

    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> subcategories, Long categoryId, Long subcategoryId) {
        try {
            return CollectionModel.of(
                    StreamSupport.stream(subcategories.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                    linkTo(methodOn(SubProductController.class).getSubcategoryProducts(categoryId, subcategoryId)).withSelfRel(),
                    linkTo(methodOn(SubcategoryController.class).getCategorySubcategoryById(categoryId, subcategoryId)).withRel("subcategory").expand(),
                    linkTo(methodOn(CategoryController.class).getCategoryById(categoryId)).withRel("category").expand()
            );
        } catch (NotFoundException ex) {
            return null;
        }
    }

    /**
     * Converts a Product to a JSON representation.
     *
     * @param product : the product to convert.
     * @return : EntityModel of the product.
     */
    public EntityModel<Product> toModel(Product product) {
        try {
            return EntityModel.of(product,
                    linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel(),
                    linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products").expand()
            );
        } catch (NotFoundException ex) {
            return null;
        }
    }
}
