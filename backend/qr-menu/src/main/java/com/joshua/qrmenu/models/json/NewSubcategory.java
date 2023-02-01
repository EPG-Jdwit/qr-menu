package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSubcategory {

    @JsonProperty("name")
    private String name;

    @JsonProperty("products")
    private Set<Long> products;

    public NewSubcategory(String name) {
        this.name = name;
    }
}
