package com.joshua.qrmenu.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshua.qrmenu.models.json.Product;

import java.util.*;


public class JSONParser {

    public List<Product> embeddedObjectToProductList(Object object) {
        Map<String, Map<String, List<Map<String, Object>>>> json = (Map<String, Map<String, List<Map<String, Object>>>>) object;
        List<Product> products = new ArrayList<>();
        if (json.get("_embedded") != null) { // TODO: Force _embedded for empty lists?
            List<Map<String, Object>> productList = json.get("_embedded").get("productList");
            ObjectMapper objectMapper = new ObjectMapper();
            for (Map<String, Object> productMap : productList) {
                Product product = objectMapper
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .convertValue(productMap, Product.class);
                products.add(product);
            }
        }
        return products;
    }

    public Product jsonMapToProduct(Object object) {
        Map<String, Object> map = (Map<String, Object>) object;
        return new Product(
                ((Integer) map.get("id")).longValue(), // TODO: Solve this hack. For some reason output is Long but when JSON is read it's an Integer
                (String) map.get("name"),
                (double) map.get("price"),
                (String) map.get("description")
        );
    }
}
