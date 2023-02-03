package com.joshua.qrmenu.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.util.JSONParser;
import com.joshua.qrmenu.util.mocker.ProductMocker;
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

import static com.joshua.qrmenu.util.Conditions.productEqualsNewProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private static final ProductMocker productMocker = new ProductMocker();

    private static final JSONParser JSON_PARSER = new JSONParser();

    private static List<Product> products;

    private static Product product1;
    private static Product product2;

    @Test
    @Order(0)
    public void initEnvironment() throws Exception {
        // TODO : auth
    }

    @Test
    @Order(1)
    public void getProductsAtStart() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        List<Product> productList = JSON_PARSER.embeddedObjectToProductList(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(productList.size()).isEqualTo(0);

    }

    @Test
    @Order(2)
    public void createProduct() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newProduct))
        )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        product1 = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(product1).satisfies(productEqualsNewProduct(newProduct));
        assertThat(product1.getProductId()).isNotEqualTo(null);
    }

    @Test
    @Order(3)
    public void createProduct2() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        product2 = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(product2).satisfies(productEqualsNewProduct(newProduct));
        assertThat(product2).isNotEqualTo(product1);
    }

    @Test
    @Order(4)
    public void getAllProducts() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Product> productList = JSON_PARSER.embeddedObjectToProductList(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(productList.size()).isEqualTo(2);
        assertThat(productList.containsAll(Arrays.asList(product1, product2))).isTrue();
    }

    @Test
    @Order(5)
    public void getProductById() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + product1.getProductId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Product result = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isEqualTo(product1);
        assertThat(result).isNotEqualTo(product2);
    }

    @Test
    @Order(6)
    public void patchProductByIdAllFields() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Product result = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isNotEqualTo(product1);
        assertThat(result.getProductId()).isEqualTo(product1.getProductId());
        product1 = result;
    }

    @Test
    @Order(7)
    public void patchProductByIdNoFields() throws Exception {
        NewProduct newProduct = productMocker.generateNullNewProduct();
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Product result = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isEqualTo(product1);
        product1 = result;
    }

    @Test
    @Order(8)
    public void patchProductNameById() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setPrice(product1.getPrice());
        newProduct.setDescription(product1.getDescription());

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Product result = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isNotEqualTo(product1);
        assertThat(result.getProductId()).isEqualTo(product1.getProductId());
        assertThat(result.getName()).isNotEqualTo(product1.getName());
        assertThat(result.getPrice()).isEqualTo(product1.getPrice());
        assertThat(result.getDescription()).isEqualTo(product1.getDescription());
        product1 = result;
    }

    @Test
    @Order(9)
    public void patchProductPriceById() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setName(product1.getName());
        newProduct.setDescription(product1.getDescription());

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Product result = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isNotEqualTo(product1);
        assertThat(result.getProductId()).isEqualTo(product1.getProductId());
        assertThat(result.getName()).isEqualTo(product1.getName());
        assertThat(result.getPrice()).isNotEqualTo(product1.getPrice());
        assertThat(result.getDescription()).isEqualTo(product1.getDescription());
        product1 = result;
    }

    @Test
    @Order(10)
    public void patchProductDescriptionById() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setName(product1.getName());
        newProduct.setPrice(product1.getPrice());

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .patch("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Product result = JSON_PARSER.jsonMapToProduct(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(result).isNotEqualTo(product1);
        assertThat(result.getProductId()).isEqualTo(product1.getProductId());
        assertThat(result.getName()).isEqualTo(product1.getName());
        assertThat(result.getPrice()).isEqualTo(product1.getPrice());
        assertThat(result.getDescription()).isNotEqualTo(product1.getDescription());
        product1 = result;
    }

    @Test
    @Order(20)
    public void deleteProductById() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<Product> productList = JSON_PARSER.embeddedObjectToProductList(new ObjectMapper().readValue(response.getContentAsString(), Map.class));
        assertThat(productList.size()).isEqualTo(1);
        assertThat(productList.contains(product2)).isTrue();
    }

    @Test
    @Order(21)
    public void deleteByIdNotFound() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/products/" + product1.getProductId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(30)
    public void createProductNoName() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setName(null);
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(31)
    public void createProductNoPrice() throws Exception {
        NewProduct newProduct = productMocker.generateNewProduct();
        newProduct.setPrice(null);
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newProduct))
                )
                .andExpect(status().isBadRequest());
    }
}
