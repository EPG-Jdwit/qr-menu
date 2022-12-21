package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

/**
 * Class that represents a Product with all it's fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonProperty
    private Long productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("categories")
    private Set<Category> categories;
}
