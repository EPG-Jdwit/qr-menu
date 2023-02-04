package com.joshua.qrmenu.subproducts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshua.qrmenu.models.json.*;
import com.joshua.qrmenu.util.JSONParser;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class SubProductIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private static final CategoryMocker categoryMocker = new CategoryMocker();

    private static final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    private static final ProductMocker productMocker = new ProductMocker();

    private static final JSONParser JSON_PARSER = new JSONParser();

    private static Category category1;
    private static Category category2;

    private static Subcategory subcategory1;
    private static Subcategory subcategory2;
    private static Subcategory subcategory3;
    private static Subcategory subcategory4;

    private static Product product1;
    private static Product product2;
    private static Product product3;
    private static Product product4;

    @Test
    @Order(1)
    public void setUp1() throws Exception {
        // Create category1
        NewCategory newCategory = categoryMocker.generateNewCategory();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newCategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        category1 = JSON_PARSER.jsonMapToCategory(new ObjectMapper().readValue(response.getContentAsString(), Map.class));

        // Create subcategory1
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories/" + category1.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory1 = JSON_PARSER.jsonMapToSubcategory(response.getContentAsString());

        // Create products
        NewProduct newProduct = productMocker.generateNewProduct();
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        product1 = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));

        newProduct = productMocker.generateNewProduct();
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        product2 = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));

        // Create products
        newProduct = productMocker.generateNewProduct();
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        product3 = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));

        newProduct = productMocker.generateNewProduct();
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        product4 = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
    }

    @Test
    @Order(2)
    public void wrongCategoryId() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() + 1 +
                                "/subcategories/" + subcategory1.getSubcategoryId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void wrongSubcategoryId() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() +
                                "/subcategories/" + subcategory1.getSubcategoryId() + 1)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void noProducts() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() +
                                "/subcategories/" + subcategory1.getSubcategoryId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Product> result = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @Order(10)
    public void oneSubcategoryOneProductByPatch() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setName(subcategory1.getName());
        newSubcategory.setProducts(List.of(product1.getProductId()));
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Subcategory result = JSON_PARSER.jsonMapToSubcategory(response.getContentAsString());
        assertThat(result.getName()).isEqualTo(subcategory1.getName());
        assertThat(result.getSubcategoryId()).isEqualTo(subcategory1.getSubcategoryId());

        // Check if the products can be requested
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Product> products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.contains(product1)).isTrue();
        subcategory1 = result;
    }

    @Test
    @Order(11)
    public void oneSubcategoryTwoProductsByPatch() throws Exception {
        // Get the current products
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Product> products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        products.add(product2);

        // Update the subcategory
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setName(subcategory1.getName());
        newSubcategory.setProducts(products.stream().map(Product::getProductId).toList());
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Subcategory result = JSON_PARSER.jsonMapToSubcategory(response.getContentAsString());
        assertThat(result.getName()).isEqualTo(subcategory1.getName());
        assertThat(result.getSubcategoryId()).isEqualTo(subcategory1.getSubcategoryId());

        // Check if the subcategory contains all products
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.containsAll(Arrays.asList(product1, product2))).isTrue();
        subcategory1 = result;
    }

    @Test
    @Order(21)
    public void oneSubcategoryOneProductByCreate() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setProducts(
                List.of(product3.getProductId())
        );

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories/" + category1.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory2 = JSON_PARSER.jsonMapToSubcategory(response.getContentAsString());
        // Check if the products can be requested
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory2.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Product> products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.contains(product3)).isTrue();
    }

    @Test
    @Order(22)
    public void oneSubcategoryTwoProductsByCreate() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setProducts(
                Stream.of(product3.getProductId(), product2.getProductId(), product1.getProductId()).toList()
        );

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories/" + category1.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory3 = JSON_PARSER.jsonMapToSubcategory(response.getContentAsString());
        // Check if the products can be requested
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory3.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Product> products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(products.size()).isEqualTo(3);
        assertThat(products.containsAll(Arrays.asList(product1, product2, product3))).isTrue();
    }

    @Test
    @Order(30)
    public void twoCategories() throws Exception {
        // Create category2
        NewCategory newCategory = categoryMocker.generateNewCategory();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newCategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        category2 = JSON_PARSER.jsonMapToCategory(new ObjectMapper().readValue(response.getContentAsString(), Map.class));

        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        newSubcategory.setProducts(
                Stream.of(product1.getProductId(), product2.getProductId()).toList()
        );

        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories/" + category2.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory4 = JSON_PARSER.jsonMapToSubcategory(response.getContentAsString());

        // Check if both requests are correct
        // Category 1
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Product> products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.containsAll(Arrays.asList(product1, product2))).isTrue();

        // Category 2
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category2.getCategoryId() +
                                        "/subcategories/" + subcategory4.getSubcategoryId() +
                                        "/products"
                                )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        products = JSON_PARSER.embeddedObjectToProductList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.containsAll(Arrays.asList(product1, product2))).isTrue();
    }

}
