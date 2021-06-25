package com.example.realestate.controller;

import com.example.realestate.service.PriceLookUpService;
import com.example.realestate.service.dto.PriceLookUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class PriceLookUpController {

    private final PriceLookUpService priceLookUpService;

    @PostMapping("/price-look-ups/check-up")
    public ResponseEntity<PriceLookUpDto> lookUpHousePrice(@Valid @RequestBody PriceLookUpDto priceLookUpDto) {
        log.info("REST request to look up a house price");
        PriceLookUpDto result = priceLookUpService.calculatePriceLookUp(priceLookUpDto);
        priceLookUpDto.setLookUpDate(new Date());
        priceLookUpService.savePriceLookUpAsync(priceLookUpDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/price-look-ups")
    public ResponseEntity<Page<PriceLookUpDto>> getLookUpHistory(Pageable pageable) {
        log.info("REST request to get look up history");
        return ResponseEntity.ok(priceLookUpService.getPriceLookUpHistory(pageable));
    }
}
