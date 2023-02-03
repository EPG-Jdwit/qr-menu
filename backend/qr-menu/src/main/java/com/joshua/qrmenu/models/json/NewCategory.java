package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshua.qrmenu.models.validators.RequiredValidator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
