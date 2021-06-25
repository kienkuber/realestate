package com.example.realestate.controller;

import com.example.realestate.RealEstateApplication;
import com.example.realestate.entity.DistrictPrice;
import com.example.realestate.entity.PriceLookUp;
import com.example.realestate.repository.PriceLookUpRepository;
import com.example.realestate.service.dto.PriceLookUpDto;
import com.example.realestate.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RealEstateApplication.class)
@TestPropertySource(
        locations = "classpath:application-test.yml")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceLookUpControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PriceLookUpRepository priceLookUpRepository;

    private PriceLookUp priceLookUp;

    @BeforeEach
    public void initTest() {
        priceLookUp = new PriceLookUp();
        priceLookUp.setDistrictPrice(new DistrictPrice(1L));
        priceLookUp.setLookUpDate(new Date());
        priceLookUp.setArea(1f);
    }

    @Test
    public void lookUpPriceSuccess() throws Exception {
        PriceLookUpDto dto = new PriceLookUpDto();
        dto.setArea(10.22f);
        dto.setDistrictPriceId(1L);

        mvc.perform(post("/api/price-look-ups/check-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.totalPrice").value("204400000"));
    }

    @Test
    public void lookUpPriceRequestNull() throws Exception {
        mvc.perform(post("/api/price-look-ups/check-up")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void lookUpPriceAreaNull() throws Exception {
        PriceLookUpDto dto = new PriceLookUpDto();
        dto.setDistrictPriceId(1L);

        mvc.perform(post("/api/price-look-ups/check-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void lookUpPriceDistrictPriceIdNull() throws Exception {
        PriceLookUpDto dto = new PriceLookUpDto();
        dto.setArea(10.22f);

        mvc.perform(post("/api/price-look-ups/check-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void lookUpPriceDistrictPriceIdNotExist() throws Exception {
        PriceLookUpDto dto = new PriceLookUpDto();
        dto.setArea(10.22f);
        dto.setDistrictPriceId(1000L);

        mvc.perform(post("/api/price-look-ups/check-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void lookUpPriceAreaNegativeValue() throws Exception {
        PriceLookUpDto dto = new PriceLookUpDto();
        dto.setArea(-10.22f);
        dto.setDistrictPriceId(1L);

        mvc.perform(post("/api/price-look-ups/check-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void lookUpPriceGetHistorySuccess() throws Exception {
        priceLookUpRepository.saveAndFlush(priceLookUp);

        mvc.perform(get("/api/price-look-ups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].area").value(1.0))
                .andExpect(jsonPath("$.content[0].districtPriceId").value(1));
    }

    @Test
    public void lookUpPriceGetHistorySuccessEmptyList() throws Exception {

        mvc.perform(get("/api/price-look-ups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty());
    }

}
