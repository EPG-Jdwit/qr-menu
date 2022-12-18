package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct {

    @JsonProperty("name")
    private String name;

//    @JsonProperty("price")
//    private double price;
}
