package com.joshua.qrmenu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.json.Subcategory;

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
                (Long) map.get("id"),
                (String) map.get("name"),
                (double) map.get("price"),
                (String) map.get("description")
        );
    }

    public List<Category> embeddedObjectToCategoryList(Object object) {
        Map<String, Map<String, List<Map<String, Object>>>> json = (Map<String, Map<String, List<Map<String, Object>>>>) object;
        List<Category> categories = new ArrayList<>();
        if (json.get("_embedded") != null) { // TODO: Force _embedded for empty lists?
            List<Map<String, Object>> categoryList = json.get("_embedded").get("categoryList");
            ObjectMapper objectMapper = new ObjectMapper();
            for (Map<String, Object> categoryMap : categoryList) {
                Category category = objectMapper
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .convertValue(categoryMap, Category.class);
                categories.add(category);
            }
        }
        return categories;
    }

    public Category jsonMapToCategory(Object object) {
        Map<String, Object> map = (Map<String, Object>) object;
        return new Category(
                (Long) map.get("id"),
                (String) map.get("name"),
                (Integer) map.get("orderNr")
        );
    }

    public List<Subcategory> embeddedObjectToSubcategoryList(Object object) {
        Map<String, Map<String, List<Map<String, Object>>>> json = (Map<String, Map<String, List<Map<String, Object>>>>) object;
        List<Subcategory> subcategories = new ArrayList<>();
        if (json.get("_embedded") != null) { // TODO: Force _embedded for empty lists?
            List<Map<String, Object>> subcategoryList = json.get("_embedded").get("subcategoryList");
            ObjectMapper objectMapper = new ObjectMapper();
            for (Map<String, Object> subcategoryMap : subcategoryList) {
                Subcategory subcategory = objectMapper
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .convertValue(subcategoryMap, Subcategory.class);
                subcategories.add(subcategory);
            }
        }
        return subcategories;
    }

    public Subcategory jsonMapToSubcategory(String string) {
//        Map<String, Object> map = (Map<String, Object>) object;
//        return new Subcategory(
//                (Long) map.get("id"),
//                (String) map.get("name")
//        );
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .readValue(string, Subcategory.class);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }
}
