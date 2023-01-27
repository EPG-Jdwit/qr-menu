package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {

    @JsonProperty("id")
    private Long subcategoryId;

    @JsonProperty("name")
    private String name;

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
