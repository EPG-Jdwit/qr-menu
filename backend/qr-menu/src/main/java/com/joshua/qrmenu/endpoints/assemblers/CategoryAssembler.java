package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.models.json.Category;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Assembling our internal data into JSON representation.
 */
@Component
public class CategoryAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {
    @Override
    public CollectionModel<EntityModel<Category>> toCollectionModel(Iterable<? extends Category> categories) {
        return CollectionModel.of(
                StreamSupport.stream(categories.spliterator(), false).map(this::toModel).collect(Collectors.toList())
        );
    }

    @Override
    public EntityModel<Category> toModel(Category category) {
        return EntityModel.of(category);
    }
}
