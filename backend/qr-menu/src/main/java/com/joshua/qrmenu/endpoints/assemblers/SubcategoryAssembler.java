package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.endpoints.CategoryController;
import com.joshua.qrmenu.endpoints.SubProductController;
import com.joshua.qrmenu.endpoints.SubcategoryController;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Subcategory;
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
public class SubcategoryAssembler implements RepresentationModelAssembler<Subcategory, EntityModel<Subcategory>> {

    /**
     * Converts a List of Subcategory to a JSON representation of a subcategoryList with an extra _links property.
     *
     * @param subcategories : A collection for which toModel must be applied to each element.
     * @return : CollectionModel of the output with links.
     */
    @Override
    public CollectionModel<EntityModel<Subcategory>> toCollectionModel(Iterable<? extends Subcategory> subcategories) {
        return CollectionModel.of(
                StreamSupport.stream(subcategories.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(SubcategoryController.class).getAllSubcategories()).withSelfRel()

        );
    }

    /**
     * Converts a List of Subcategories of a Category to a JSON representation of a subcategoryList with an extra _links property.
     *
     * @param subcategories : A collection for which toModel must be applied to each element.
     * @return : CollectionModel of the output with links.
     */
    public CollectionModel<EntityModel<Subcategory>> toCollectionModel(Iterable<? extends Subcategory> subcategories, Long categoryId) {
        try {
            return CollectionModel.of(
                    StreamSupport.stream(subcategories.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                    linkTo(methodOn(SubcategoryController.class).getAllCategorySubcategories(categoryId)).withSelfRel()

            );
        } catch (NotFoundException ex) {
            return null;
        }
    }

    /**
     * Converts a Subcategory to a JSON representation with HATEAOS links.
     *
     * @param subcategory : A Subcategory with data to be converted.
     * @return : An EntityModel of the Subcategory with multiple HATEOAS links to the endpoints.
     */
    @Override
    public EntityModel<Subcategory> toModel(Subcategory subcategory) {
        try {
            return EntityModel.of(subcategory,
                    linkTo(methodOn(SubcategoryController.class).getCategorySubcategoryById(subcategory.getCategoryId(), subcategory.getSubcategoryId())).withSelfRel(),
                    linkTo(methodOn(SubProductController.class).getSubcategoryProducts(subcategory.getCategoryId(), subcategory.getSubcategoryId())).withRel("products").expand(),
                    linkTo(methodOn(SubcategoryController.class).getAllCategorySubcategories(subcategory.getCategoryId())).withRel("subcategories").expand(),
                    linkTo(methodOn(CategoryController.class).getCategoryById(subcategory.getCategoryId())).withRel("category").expand()
                    );
        } catch (NotFoundException ex) {
            return null;
        }
    }
}
