package com.kp.merchantonboardingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.merchantonboardingservice.MerchantOnboardingServiceApplication;
import com.kp.merchantonboardingservice.models.Merchant;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MerchantControllerTest extends BaseControllerTest {
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
    void createMerchant() throws Exception {
        Merchant merchant = new Merchant(1, "test-merchant","ACTIVE");
        this.mockMvc.perform(post("/merchants/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .string(objectMapper.writeValueAsString(merchant)));
    }

    @Order(2)
    @Test
    void updateMerchant() throws Exception {
        Merchant merchant = new Merchant(1, "test-merchant-updated","ACTIVE");
        this.mockMvc.perform(put("/merchants/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .string(objectMapper.writeValueAsString(merchant)));
    }

    @Order(3)
    @Test
    void getMerchant() throws Exception {
        this.mockMvc.perform(get("/merchants/{merchantId}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Order(4)
    @Test
    void getMerchantFailure() throws Exception {
        this.mockMvc.perform(get("/merchants/{merchantId}", 0))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Order(5)
    @Test
    void deleteMerchant() throws Exception {
        this.mockMvc.perform(delete("/merchants/{merchantId}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(6)
    @Test
    void getMerchants() throws Exception {
        createMerchant();
        this.mockMvc.perform(get("/merchants/page/{pageNumber}/size/{pageSize}", 1, 20))
                .andDo(print())
                .andExpect(status().isOk());
    }
}