package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.endpoints.MemberController;
import com.joshua.qrmenu.endpoints.ProductController;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Product;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembling our internal data into JSON representation.
 */
@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    /**
     * Converts a List of Product to a JSON representation of a productList.
     *
     * @param products : products to convert.
     * @return : CollectionModel of the EntityModels of products.
     */
    @Override
    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> products) {
        return CollectionModel.of(
                StreamSupport.stream(products.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products").expand()
        );
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
                    linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products").expand(),
                    linkTo(methodOn(MemberController.class).getProductCategories(product.getProductId())).withRel("productCategories").expand()
            );
        } catch (NotFoundException ex) {
            return null;
        }
    }
}
