package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object representing a Category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @JsonProperty("id")
    private Long categoryId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("orderNr")
    private int orderNr;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Category category)) {
            return false;
        }
        return categoryId.equals(category.getCategoryId()) &&
                name.equals(category.getName());
    }
}
