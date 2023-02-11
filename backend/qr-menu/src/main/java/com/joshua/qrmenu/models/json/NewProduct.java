package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshua.qrmenu.models.validators.RequiredValidator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import lombok.*;

import java.util.List;

/**
 * Data Transfer Object representing a NewCategory without an ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct {

    // Name must be unique and is required when creating a new Product.
    @JsonProperty("name")
    @RequiredValidator(on = {ValidatorMode.Create})
    private String name;

    // Price is required when creating a new Product
    @JsonProperty("price")
    @RequiredValidator(on = {ValidatorMode.Create})
    private Double price;

    @JsonProperty("description")
    private String description;

    private List<String> allergenicList;
}
