package com.joshua.qrmenu.endpoints.assemblers;

import com.joshua.qrmenu.endpoints.CategoryController;
import com.joshua.qrmenu.endpoints.ProductController;
import com.joshua.qrmenu.endpoints.RootController;
import com.joshua.qrmenu.models.json.Root;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembling our internal data into JSON representation with HATEOAS links.
 */
@Component
public class RootAssembler implements RepresentationModelAssembler<Root, EntityModel<Root>> {

    /**
     * Assembles a JSON representation of a root endpoint to navigate to other endpoints using the provided HATEAOS links.
     * @param entity : the root (empty) which is used to add navigation links
     * @return : EntityModel of the root: a collection of links.
     */
    @Override
    public EntityModel<Root> toModel(Root entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(RootController.class).getAllPaths()).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories").expand(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products").expand()
        );
    }
}
