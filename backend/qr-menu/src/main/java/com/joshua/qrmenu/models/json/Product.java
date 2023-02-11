package com.joshua.qrmenu.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object representing a Product.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonProperty("id")
    private Long productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("description")
    private String description;

    @Override
    public String toString() {
        return String.format(
                "Product[id=%d, name='%s']",
                productId, name);
    }

    @JsonProperty("allergenics")
    public List<String> allergenicList;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Product product)) {
            return false;
        }
        return productId.equals(product.getProductId()) &&
                name.equals(product.getName()) &&
                Double.compare(price, product.getPrice()) == 0 &&
                description.equals(product.getDescription());
    }
}
