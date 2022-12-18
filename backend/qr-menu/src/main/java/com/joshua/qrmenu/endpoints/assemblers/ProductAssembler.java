package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.models.json.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
                StreamSupport.stream(products.spliterator(), false).map(this::toModel).collect(Collectors.toList())
                );
    }

    /**
     * Converts a Product to a JSON representation.
     *
     * @param product : the product to convert.
     * @return : EntityModel of the product.
     */
    @Override
    public EntityModel<Product> toModel(Product product) {
        return EntityModel.of(product);
    }
}
