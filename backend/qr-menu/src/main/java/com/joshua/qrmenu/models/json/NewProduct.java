package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshua.qrmenu.models.validators.RequiredValidator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import lombok.*;

/**
 * Class that represents a new Product, which does not yet have an ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct {

    @JsonProperty("name")
    @RequiredValidator(on = {ValidatorMode.Create})
    private String name;

    @JsonProperty("price")
    @RequiredValidator(on = {ValidatorMode.Create})
    private Double price;

    @JsonProperty("description")
    private String description;
}
