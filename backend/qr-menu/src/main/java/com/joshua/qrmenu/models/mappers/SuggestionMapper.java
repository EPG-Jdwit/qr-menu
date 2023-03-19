package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.SuggestionEntity;
import com.joshua.qrmenu.models.json.NewSuggestion;
import com.joshua.qrmenu.models.json.Suggestion;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

/**
 * Maps Data Transfer Objects to Entities and back.
 */
@Component
public class SuggestionMapper implements Mapper<SuggestionEntity, Suggestion, NewSuggestion> {

    private final TypeMap<SuggestionEntity, Suggestion> suggestionEntitySuggestionTypeMap;

    private final TypeMap<NewSuggestion, SuggestionEntity> newSuggestionToSuggestionEntityTypeMap;

    /**
     * Constructor
     */
    public SuggestionMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        suggestionEntitySuggestionTypeMap = modelMapper
                .typeMap(SuggestionEntity.class, Suggestion.class);
        newSuggestionToSuggestionEntityTypeMap = modelMapper
                .typeMap(NewSuggestion.class, SuggestionEntity.class);
    }

    /**
     * Maps a NewSuggestion to NewSuggestion.
     * @param newSuggestion : A NewSuggestion with data.
     * @return : A NewSuggestion with fields filled with the corresponding data from the input.
     */
    @Override
    public SuggestionEntity newJsonToEntity(NewSuggestion newSuggestion) {
        return newSuggestionToSuggestionEntityTypeMap.map(newSuggestion);
    }

    /**
     * Maps a NewSuggestion to a Suggestion.
     * @param suggestionEntity : A NewSuggestion with data.
     * @return : A Suggestion with fields filled with the corresponding data from the entity.
     */
    @Override
    public Suggestion entityToJson(SuggestionEntity suggestionEntity) {
        return suggestionEntitySuggestionTypeMap.map(suggestionEntity);
    }
}
