package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @JsonProperty
    private Long categoryId;

    @JsonProperty("name")
    private String name;

    @JsonProperty
    private Set<Product> products;
}
