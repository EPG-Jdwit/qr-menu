package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.endpoints.CategoryController;
import com.joshua.qrmenu.endpoints.SubcategoryController;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
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
public class CategoryAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {
    @Override
    public CollectionModel<EntityModel<Category>> toCollectionModel(Iterable<? extends Category> categories) {
        return CollectionModel.of(
                StreamSupport.stream(categories.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withSelfRel()
        );
    }

    @Override
    public EntityModel<Category> toModel(Category category) {
        try {
            return EntityModel.of(category,
                    linkTo(methodOn(CategoryController.class).getCategoryById(category.getCategoryId())).withSelfRel(),
                    linkTo(methodOn(SubcategoryController.class).getAllCategorySubcategories(category.getCategoryId())).withRel("subcategories").expand(),
                    linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories").expand()
            );
        } catch (NotFoundException ex) {
            return null;
        }
    }
}
