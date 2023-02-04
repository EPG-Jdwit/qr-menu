package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshua.qrmenu.models.validators.OrderValidator;
import com.joshua.qrmenu.models.validators.RequiredValidator;
import com.joshua.qrmenu.models.validators.ValidatorMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSubcategory {

    @JsonProperty("name")
    @RequiredValidator(on = {ValidatorMode.Create})
    private String name;

    @JsonProperty("products")
    private List<Long> products;

    @JsonProperty("orderNr")
    @OrderValidator(on = {ValidatorMode.Create, ValidatorMode.Update})
    private int orderNr;

    public NewSubcategory(String name) {
        this.name = name;
    }
}
