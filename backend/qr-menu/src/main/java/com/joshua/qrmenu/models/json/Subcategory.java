package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object representing a Subcategory.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {

    @JsonProperty("id")
    private Long subcategoryId;

    // Name must be unique within the same category and is required when creating a new Subcategory.
    @JsonProperty("name")
    private String name;

    @JsonIgnore
    private Long categoryId;

    @JsonProperty("orderNr")
    private int orderNr;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Subcategory subcategory)) {
            return false;
        }
        return subcategoryId.equals(subcategory.getSubcategoryId()) &&
                name.equals(subcategory.getName());
    }
}
