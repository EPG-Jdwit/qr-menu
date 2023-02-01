package com.joshua.qrmenu.subcategory;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.util.JSONParser;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.joshua.qrmenu.util.Conditions.subcategoryEqualsNewSubcategory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class SubcategoryIntegrationTest {
    @Autowired
    private MockMvc mvc;

    private static final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    private static final CategoryMocker categoryMocker = new CategoryMocker();

    private static final JSONParser JSON_PARSER = new JSONParser();

    private static List<Subcategory> subcategories;

    private static Subcategory subcategory1;

    private static Subcategory subcategory2;

    private static Subcategory subcategory3;

    private static Category category1;

    private static Category category2;


    @Test
    @Order(0)
    public void initEnvironment() throws Exception {
        // TODO: auth
    }

    @Test
    @Order(1)
    public void createCategories() throws Exception {
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

        newCategory = categoryMocker.generateNewCategory();
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newCategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        category2 = JSON_PARSER.jsonMapToCategory(new ObjectMapper().readValue(response.getContentAsString(), Map.class));

    }
    @Test
    @Order(2)
    public void getAllCategorySubcategoriesAtStart() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() + "/subcategories")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        List<Subcategory> subcategoryList = JSON_PARSER.embeddedObjectToSubcategoryList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(subcategoryList.size()).isEqualTo(0);
    }

    @Test
    @Order(3)
    public void getSubcategoryByIdAtStart() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() + "/subcategories/" + 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(10)
    public void createSubcategory() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/categories/" + category1.getCategoryId() + "/subcategories")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newSubcategory))
        )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory1 = JSON_PARSER.jsonMapToSubcategory(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );

        assertThat(subcategory1).satisfies(subcategoryEqualsNewSubcategory(newSubcategory));
        assertThat(subcategory1.getSubcategoryId()).isNotEqualTo(null);
    }

    @Test
    @Order(11)
    public void getSubcategoryByWrongSubcategoryId() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() +
                                "/subcategories/" + (subcategory1.getSubcategoryId() + 1))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(12)
    public void getSubcategoriesWrongCategoryId() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + (category1.getCategoryId() + 1) +
                                "/subcategories/" + subcategory1.getSubcategoryId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(15)
    public void createOtherSubcategorySameCategory() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories/" + category1.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory2 = JSON_PARSER.jsonMapToSubcategory(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );

        assertThat(subcategory2).satisfies(subcategoryEqualsNewSubcategory(newSubcategory));
        assertThat(subcategory2.getSubcategoryId()).isNotEqualTo(null);
        assertThat(subcategory2).isNotEqualTo(subcategory1);
    }
    @Test
    @Order(20)
    public void getAllCategorySubcategories() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Subcategory> subcategoryList = JSON_PARSER.embeddedObjectToSubcategoryList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(subcategoryList.size()).isEqualTo(2);
        assertThat(subcategoryList.containsAll(Arrays.asList(subcategory1, subcategory2))).isTrue();
    }

    @Test
    @Order(21)
    public void getAllCategorySubcategoriesEmpty() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category2.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Subcategory> subcategoryList = JSON_PARSER.embeddedObjectToSubcategoryList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(subcategoryList.size()).isEqualTo(0);
    }

    @Test
    @Order(25)
    public void getSubcategoryById() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories/" + category1.getCategoryId() +
                                "/subcategories/" + subcategory1.getSubcategoryId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Subcategory subcategory = JSON_PARSER.jsonMapToSubcategory(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(subcategory).isEqualTo(subcategory1);
        assertThat(subcategory).isNotEqualTo(subcategory2);
    }

    @Test
    @Order(30)
    public void createSubcategoryDifferentCategory() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/categories/" + category2.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSubcategory))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        subcategory3 = JSON_PARSER.jsonMapToSubcategory(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );

        assertThat(subcategory3).satisfies(subcategoryEqualsNewSubcategory(newSubcategory));
        assertThat(subcategory3.getSubcategoryId()).isNotEqualTo(null);
        assertThat(subcategory3).isNotEqualTo(subcategory1);
        assertThat(subcategory3).isNotEqualTo(subcategory2);
    }

    // Test patching a category

    @Test
    @Order(35)
    public void patchCategory() throws Exception {
        // Patch the category
        NewCategory newCategory = categoryMocker.generateNewCategory();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/categories/" + category1.getCategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newCategory))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Category result = JSON_PARSER.jsonMapToCategory(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result.getCategoryId()).isEqualTo(category1.getCategoryId());
        assertThat(result.getName()).isNotEqualTo(category1.getName());

        // Check that this didn't change any subcategories
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() + "/subcategories")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Subcategory> subcategoryList = JSON_PARSER.embeddedObjectToSubcategoryList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(subcategoryList.size()).isEqualTo(2);
        assertThat(subcategoryList.containsAll(Arrays.asList(subcategory1, subcategory2))).isTrue();
    }

    // Test patching a subcategory

    @Test
    @Order(40)
    public void patchSubcategoryByIdAllFields() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
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
        Subcategory result = JSON_PARSER.jsonMapToSubcategory(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(result.getSubcategoryId()).isEqualTo(subcategory1.getSubcategoryId());
        assertThat(result.getName()).isNotEqualTo(subcategory1.getName());
        subcategory1 = result;
    }

    @Test
    @Order(41)
    public void patchSubcategoryByIdNoFields() throws Exception {
        NewSubcategory newSubcategory = subcategoryMocker.generateNewSubcategory();
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
        Subcategory result = JSON_PARSER.jsonMapToSubcategory(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class)
        );
        assertThat(result.getSubcategoryId()).isEqualTo(subcategory1.getSubcategoryId());
    }

    @Test
    @Order(45)
    public void deleteSubcategoryById() throws Exception {
        // Verify that subcategory is present
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        // Delete the subcategory
        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
        // Verify that a GET request with ID returns Not Found
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        // Verify that a GET request no longer returns the deleted subcategory
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId() +
                                        "/subcategories" )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Subcategory> subcategoryList = JSON_PARSER.embeddedObjectToSubcategoryList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(subcategoryList.size()).isEqualTo(1);
        assertThat(subcategoryList.contains(subcategory2)).isTrue();
        assertThat(subcategoryList.contains(subcategory1)).isFalse();

        // Finally verify that the subcategory also doesn't show up in another category
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category2.getCategoryId() +
                                        "/subcategories" )
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        subcategoryList = JSON_PARSER.embeddedObjectToSubcategoryList(
                new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(subcategoryList.size()).isEqualTo(1);
        assertThat(subcategoryList.contains(subcategory3)).isTrue();
    }

    @Test
    @Order(46)
    public void deleteByIdNotFound() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/categories/" + category1.getCategoryId() +
                                        "/subcategories/" + subcategory1.getSubcategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
