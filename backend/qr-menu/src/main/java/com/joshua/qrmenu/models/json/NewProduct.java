package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Class that represents a new Product, which does not yet have an ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("description")
    private String description;
}
