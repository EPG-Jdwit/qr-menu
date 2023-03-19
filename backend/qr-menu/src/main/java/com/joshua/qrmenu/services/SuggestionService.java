package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.ShallowCopy;
import com.joshua.qrmenu.models.entities.SuggestionEntity;
import com.joshua.qrmenu.models.json.NewSuggestion;
import com.joshua.qrmenu.models.json.Suggestion;
import com.joshua.qrmenu.models.mappers.SuggestionMapper;
import com.joshua.qrmenu.models.validators.Validator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import com.joshua.qrmenu.repositories.SuggestionRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to handle all business logic concerning suggestions.
 */
@Service
public class SuggestionService extends AbstractService {
    private final SuggestionRepository suggestionRepository;

    private final SuggestionMapper suggestionMapper;

    /**
     * Constructor for a SuggestionService.
     * @param suggestionRepository: A SuggestionRepository.
     * @param suggestionMapper: A SuggestionMapper to map Data Transfer Objects to Entities and back.
     */
    public SuggestionService(SuggestionRepository suggestionRepository, SuggestionMapper suggestionMapper) {
        this.suggestionRepository = suggestionRepository;
        this.suggestionMapper = suggestionMapper;
    }

    /**
     * Retrieves all existing suggestions
     * @return : A List of all Suggestions.
     */
    public List<Suggestion> getAll() {
        List<SuggestionEntity> suggestionEntityList = suggestionRepository.findAll(Sort.by(Sort.Direction.ASC, "orderNr"));

        return suggestionEntityList
                .stream()
                .map(suggestionMapper::entityToJson)
                .collect(Collectors.toList());
    }

    /**
     * Return a suggestion with a specific ID if found.
     * @param id : ID of the SuggestionEntity to be retrieved.
     * @return : The suggestion with the given ID,
     * @throws NotFoundException : When the ID doesn't correspond to an existing SuggestionEntity.
     */
    public Suggestion getSuggestionById(Long id) throws NotFoundException {
        SuggestionEntity suggestionEntity = parseOptional(suggestionRepository.findById(id));
        return suggestionMapper.entityToJson(suggestionEntity);
    }

    /**
     * Create a new suggestion.
     * @param newSuggestion : An object with all data to create the new SuggestionEntity.
     * @return : A Suggestion representing the newly created SuggestionEntity.
     * @throws InputException : When a required field wasn't provided.
     * @throws AlreadyExistsException : When a SuggestionEntity already exists with the same url.
     */
    public Suggestion createSuggestion(NewSuggestion newSuggestion) throws InputException, AlreadyExistsException {
        // Validate input
        Validator validator = new Validator();
        validator.validate(newSuggestion, ValidatorMode.Create);

        // Check if a SuggestionEntity with the same url already exists
        if(suggestionRepository.findByUrl(newSuggestion.getUrl()).isPresent()) {
            throw new AlreadyExistsException("Suggestion with the url '" + newSuggestion.getUrl() + "' already exists.");
        }

        SuggestionEntity suggestionEntity = suggestionMapper.newJsonToEntity(newSuggestion);
        suggestionEntity = suggestionRepository.save(suggestionEntity);
        return suggestionMapper.entityToJson(suggestionEntity);
    }

    /**
     * Delete a SuggestionEntity with a specific ID if found.
     *
     * @param id : ID of the SuggestionEntity to be deleted.
     * @throws NotFoundException : When the ID doesn't correspond to an existing SuggestionEntity.
     */
    public void deleteSuggestionById(Long id) throws NotFoundException {
        if (suggestionRepository.existsById(id)) {
            suggestionRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Update data of an existing SuggestionEntity with a specific ID if found.
     *
     * @param id : The ID of the SuggestionEntity to be updated.
     * @param newSuggestion : An Object containing data to be changed.
     * @return : The Suggestion with the updated fields.
     * @throws NotFoundException : When the ID doesn't correspond to an existing SuggestionEntity.
     * @throws AlreadyExistsException : When a different SuggestionEntity already exists with the same url.
     */
    public Suggestion patchSuggestionById(Long id, NewSuggestion newSuggestion) throws NotFoundException, AlreadyExistsException {
        SuggestionEntity originalEntity = parseOptional(suggestionRepository.findById(id));
        SuggestionEntity newEntity = suggestionMapper.newJsonToEntity(newSuggestion);

        // Check if a SuggestionEntity with the same url already exists
        if( newEntity.getUrl() != null
                && (!newEntity.getUrl().equals(originalEntity.getUrl()))
                && suggestionRepository.findByUrl(newSuggestion.getUrl()).isPresent()) {
            throw new AlreadyExistsException("Suggestion with the url '" + newSuggestion.getUrl() + "' already exists.");
        }

        ShallowCopy.copyFieldsExceptNull(newEntity, originalEntity);

        suggestionRepository.save(originalEntity);
        return suggestionMapper.entityToJson(originalEntity);
    }
}
