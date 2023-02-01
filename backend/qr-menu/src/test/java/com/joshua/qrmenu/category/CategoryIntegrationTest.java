package com.joshua.qrmenu.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.util.JSONParser;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
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

import static com.joshua.qrmenu.util.Conditions.categoryEqualsNewCategory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class CategoryIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private static final CategoryMocker categoryMocker = new CategoryMocker();

    private static final JSONParser JSON_PARSER = new JSONParser();

    private static List<Category> categories;

    private static Category category1;

    private static Category category2;

    @Test
    @Order(0)
    public void initEnvironment() throws Exception {
        // TODO: auth
    }

    @Test
    @Order(1)
    public void getCategoryAtStart() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        List<Category> categoryList = JSON_PARSER.embeddedObjectToCategoryList(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(categoryList.size()).isEqualTo(0);
    }

    @Test
    @Order(2)
    public void createCategory() throws Exception {
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
        assertThat(category1).satisfies(categoryEqualsNewCategory(newCategory));
        assertThat(category1.getCategoryId()).isNotEqualTo(null);
    }

    @Test
    @Order(3)
    public void getCategoryByIdNotFound() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + (category1.getCategoryId() + 1))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(3)
    public void getCategoryByNameNotFound() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/category?name=" + category1.getName() + "a")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(4)
    public void createCategory2() throws Exception{
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
        assertThat(category2).satisfies(categoryEqualsNewCategory(newCategory));
        assertThat(category2.getCategoryId()).isNotEqualTo(null);
        assertThat(category2).isNotEqualTo(category1);
    }

    @Test
    @Order(5)
    public void getAllCategories() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/categories")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Category> categoryList = JSON_PARSER.embeddedObjectToCategoryList(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(categoryList.size()).isEqualTo(2);
        assertThat(categoryList.containsAll(Arrays.asList(category1, category2))).isTrue();
    }

    @Test
    @Order(5)
    public void getCategoryById() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Category result = JSON_PARSER.jsonMapToCategory(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isEqualTo(category1);
        assertThat(result).isNotEqualTo(category2);

    }

    @Test
    @Order(5)
    public void getCategoryByName() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/category?name=" + category1.getName())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Category result = JSON_PARSER.jsonMapToCategory(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isEqualTo(category1);
        assertThat(result).isNotEqualTo(category2);

    }

    @Test
    @Order(6)
    public void patchCategoryByIdAllFields() throws Exception {
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
        assertThat(result).isNotEqualTo(category1);
        assertThat(result.getCategoryId()).isEqualTo(category1.getCategoryId());
        category1 = result;
    }

    @Test
    @Order(7)
    public void patchCategoryByIdNoFields() throws Exception {
        NewCategory newCategory = categoryMocker.generateNullNewCategory();
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
        assertThat(result).isEqualTo(category1);
        category1 = result;
    }

    @Test
    @Order(8)
    public void patchCategoryNameById() throws Exception {
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
        category1 = result;
    }

    @Test
    @Order(20)
    public void deleteCategoryById() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/categories/" + category1.getCategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/" + category1.getCategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Category> categoryList = JSON_PARSER.embeddedObjectToCategoryList(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(categoryList.size()).isEqualTo(1);
        assertThat(categoryList.contains(category2)).isTrue();
        assertThat(categoryList.contains(category1)).isFalse();
    }

    @Test
    @Order(21)
    public void deleteByIdNotFound() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/categories/" + category1.getCategoryId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
