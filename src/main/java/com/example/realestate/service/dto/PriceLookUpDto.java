package com.example.realestate.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PriceLookUpDto {

    private Long id;

    @NotNull
    private Long districtPriceId;

    @NotNull
    @Min(value = 0)
    private Float area;

    private Date lookUpDate;

    private Long totalPrice;
}
