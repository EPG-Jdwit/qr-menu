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
 * Assembling our internal data into JSON representation with HATEOAS links.
 */
@Component
public class CategoryAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {

    /**
     * Converts a List of Categories to a JSON representation of a categoryList with an extra _links property.
     * @param categories : A collection for which toModel must be applied to each element.
     * @return : A CollectionModel of the output with links.
     */
    @Override
    public CollectionModel<EntityModel<Category>> toCollectionModel(Iterable<? extends Category> categories) {
        return CollectionModel.of(
                StreamSupport.stream(categories.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withSelfRel()
        );
    }

    /**
     * Converts a Category to a JSON representation with HATEAOS links.
     * @param category : A Category with data to be converted.
     * @return : An EntityModel of the Category with multiple HATEAOS links to endpoints.
     */
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
