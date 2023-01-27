package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.models.json.Subcategory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SubcategoryAssembler implements RepresentationModelAssembler<Subcategory, EntityModel<Subcategory>> {

    @Override
    public CollectionModel<EntityModel<Subcategory>> toCollectionModel(Iterable<? extends Subcategory> subcategories) {
        return CollectionModel.of(
                StreamSupport.stream(subcategories.spliterator(), false).map(this::toModel).collect(Collectors.toList())
//                linkTo(methodOn(SubcategoryController.class).getAllCategorySubcategories().withRel("subcategories").expand())
        );
    }

    @Override
    public EntityModel<Subcategory> toModel(Subcategory subcategory) {
        return EntityModel.of(subcategory);
    }
}
