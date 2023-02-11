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

/**
 * Assembling our internal data into JSON representation with HATEOAS links.
 */
@Component
public class SubProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    // TODO: Not used due to needing categoryId and subcategoryId, find a way to extract out of Iterable
    @Override
    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> subcategories) {
        return null;
    }

    /**
     * Converts a list of Products to a JSON representation of a productList with an extra _links property.
     * This is similar to the method in ProductAssembler but requires extra links due to being called in a different manner,
     * ie : links to the catergory and subcategory.
     * @param subcategories : A collection for with toModel must be applied to each element.
     * @param categoryId : The id of the category, used to build the links.
     * @param subcategoryId : The id of the subcategory, used to build the links.
     * @return : CollectionModel of the output with links.
     */
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
     * Converts a Product to a JSON representation. NOTE: identical to ProductAssembler
     *
     * @param product : the product to convert.
     * @return : EntityModel of the product with multiple HATEAOS links to endpoints.
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
