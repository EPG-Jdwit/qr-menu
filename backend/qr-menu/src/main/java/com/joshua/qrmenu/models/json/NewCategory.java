package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshua.qrmenu.models.validators.OrderValidator;
import com.joshua.qrmenu.models.validators.RequiredValidator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a NewCategory without an ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategory {

    /**
     * Name must be unique and is required when creating a new Category.
     */
    @JsonProperty("name")
    @RequiredValidator(on = {ValidatorMode.Create})
    private String name;

    @JsonProperty("orderNr")
    @OrderValidator(on = {ValidatorMode.Create, ValidatorMode.Update})
    private int orderNr;

}
