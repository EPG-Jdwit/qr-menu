package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.SuggestionAssembler;
import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.NewSuggestion;
import com.joshua.qrmenu.models.json.Suggestion;
import com.joshua.qrmenu.services.SuggestionService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint to manage the suggestions
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SuggestionController extends BaseController {

    private final SuggestionService suggestionService;

    private final SuggestionAssembler suggestionAssembler;

    /**
     * Constructor.
     *
     * @param suggestionService: A SuggestionService.
     * @param suggestionAssembler : A SuggestionAssembler to create EntityModels of Suggestions.
     */
    public SuggestionController(SuggestionService suggestionService, SuggestionAssembler suggestionAssembler) {
        this.suggestionService = suggestionService;
        this.suggestionAssembler = suggestionAssembler;
    }

    /**
     * Retrieve all suggestions.
     *
     * @return : A CollectionModel of the list of all existing suggestions.
     */
    @GetMapping("/suggestions")
    public CollectionModel<EntityModel<Suggestion>> getAllSuggestions() {
        return suggestionAssembler.toCollectionModel(
                suggestionService.getAll()
        );
    }

    /**
     * Retrieve a suggestion with the given ID.
     *
     * @param suggestionId : The ID of the suggestion to be retrieved.
     * @return : An EntityModel of the suggestion with the given ID if found.
     * @throws NotFoundException : When the ID doesn't correspond to an existing suggestion.
     */
    @GetMapping("/suggestions/{suggestionId}")
    public EntityModel<Suggestion> getSuggestionById(@PathVariable Long suggestionId) throws NotFoundException {
        return suggestionAssembler.toModel(suggestionService.getSuggestionById(suggestionId));
    }

    /**
     * Creates a new suggestion.
     *
     * @param newSuggestion : An Object with the data to create a new Suggestion.
     * @return : An EntityModel of the created suggestion.
     * @throws InputException : When a required field of the suggestion wasn't provided.
     * @throws AlreadyExistsException : If a suggestion already exists with the same name.
     */
    @PostMapping("/suggestions")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityModel<Suggestion> addSuggestion(@RequestBody NewSuggestion newSuggestion) throws InputException, AlreadyExistsException {
        return suggestionAssembler.toModel(suggestionService.createSuggestion(newSuggestion));
    }

    /**
     * Deletes a suggestion by ID.
     *
     * @param suggestionId : The ID of the suggestion to be deleted.
     * @throws NotFoundException : If no suggestion with the given ID was found.
     */
    @DeleteMapping("/suggestions/{suggestionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSuggestionById(@PathVariable Long suggestionId) throws NotFoundException {
        suggestionService.deleteSuggestionById(suggestionId);
    }

    /**
     * Update the data of a suggestion with a specific ID. Fields that weren't provided remain the same.
     *
     * @param suggestionId : ID of the suggestion to be updated.
     * @param newSuggestion : An Object with the data to be changed.
     * @return : An EntityModel of the changed suggestion.
     * @throws NotFoundException : When no suggestion with the given ID wasn't found.
     * @throws AlreadyExistsException : When a suggestion already exists with the same name.
     */
    @PatchMapping("/suggestions/{suggestionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Suggestion> updateSuggestionById(@PathVariable Long suggestionId, @RequestBody NewSuggestion newSuggestion) throws  NotFoundException, AlreadyExistsException {
        Suggestion suggestion = suggestionService.patchSuggestionById(suggestionId, newSuggestion);
        return suggestionAssembler.toModel(suggestion);
    }
}
