package com.joshua.qrmenu.endpoints.assemblers;


import com.joshua.qrmenu.endpoints.SuggestionController;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Suggestion;
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
public class SuggestionAssembler implements RepresentationModelAssembler<Suggestion, EntityModel<Suggestion>> {

    /**
     * Converts a List of Suggestions to a JSON representation of a suggestionList with an extra _links property.
     * @param suggestions : A collection for which toModel must be applied to each element.
     * @return : A CollectionModel of the output with links.
     */
    @Override
    public CollectionModel<EntityModel<Suggestion>> toCollectionModel(Iterable<? extends Suggestion> suggestions) {
        return CollectionModel.of(
                StreamSupport.stream(suggestions.spliterator(), false).map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(SuggestionController.class).getAllSuggestions()).withSelfRel()
        );
    }

    /**
     * Converts a Suggestion to a JSON representation with HATEAOS links.
     * @param suggestion : A Suggestion with data to be converted.
     * @return : An EntityModel of the Suggestion with multiple HATEAOS links to endpoints.
     */
    @Override
    public EntityModel<Suggestion> toModel(Suggestion suggestion) {
        try {
            return EntityModel.of(suggestion,
                    linkTo(methodOn(SuggestionController.class).getSuggestionById(suggestion.getSuggestionId())).withSelfRel(),
                    linkTo(methodOn(SuggestionController.class).getAllSuggestions()).withRel("suggestions").expand()
            );
        } catch (NotFoundException ex) {
            return null;
        }
    }
}
