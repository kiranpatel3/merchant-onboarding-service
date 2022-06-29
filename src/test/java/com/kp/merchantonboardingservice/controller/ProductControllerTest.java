package com.kp.merchantonboardingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.merchantonboardingservice.MerchantOnboardingServiceApplication;
import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest extends BaseControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Order(1)
    @Test
    void createProduct() throws Exception {
        Merchant merchant = new Merchant(2, "test-merchant","ACTIVE");
        this.mockMvc.perform(post("/merchants/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(merchant)))
                .andReturn();
        Product product = new Product(2, "test-product",15.0, merchant);
        this.mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }



    @Order(2)
    @Test
    void updateProduct() throws Exception {
        Merchant merchant = new Merchant(2, "test-merchant","ACTIVE");
        Product product = new Product(2, "test-product-updated",16.0, merchant);
        this.mockMvc.perform(put("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Order(3)
    @Test
    void getProduct() throws Exception {
        this.mockMvc.perform(get("/products/{productId}", 2))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(4)
    @Test
    void getProductFailure() throws Exception {
        this.mockMvc.perform(get("/products/{productId}", 0))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Order(5)
    @Test
    void deleteProduct() throws Exception {
        this.mockMvc.perform(delete("/products/{productId}", 2))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(6)
    @Test
    void getProducts() throws Exception {
        createProduct();
        this.mockMvc.perform(get("/products/page/{pageNumber}/size/{pageSize}", 1, 20))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(7)
    @Test
    void getProductsForSpecificMerchant() throws Exception {
        createProduct();
        this.mockMvc.perform(get("/products/merchant/{merchantId}/page/{pageNumber}/size/{pageSize}", 2,1, 20))
                .andDo(print())
                .andExpect(status().isOk());
    }
}