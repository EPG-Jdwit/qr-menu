package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a Suggestion.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {

    @JsonProperty("id")
    private Long suggestionId;

    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("orderNr")
    private int orderNr;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Suggestion suggestion)) {
            return false;
        }
        return suggestionId.equals(suggestion.getSuggestionId()) &&
                url.equals(suggestion.getUrl()) &&
                name.equals(suggestion.getName()) &&
                description.equals(suggestion.getDescription());
    }
}
