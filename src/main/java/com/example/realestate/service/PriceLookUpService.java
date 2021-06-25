package com.example.realestate.service;

import com.example.realestate.entity.DistrictPrice;
import com.example.realestate.entity.PriceLookUp;
import com.example.realestate.repository.DistrictPriceRepository;
import com.example.realestate.repository.PriceLookUpRepository;
import com.example.realestate.service.dto.PriceLookUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceLookUpService {

    private final PriceLookUpRepository priceLookUpRepository;
    private final DistrictPriceRepository districtPriceRepository;
    private final ModelMapper modelMapper;

    public PriceLookUpDto calculatePriceLookUp(PriceLookUpDto priceLookUpDto) {
        DistrictPrice districtPrice = districtPriceRepository.findById(priceLookUpDto.getDistrictPriceId())
                .orElseThrow(NoSuchElementException::new);
        Long calculatedPrice = (long)(districtPrice.getPricePerSquareMeter() * priceLookUpDto.getArea());
        priceLookUpDto.setTotalPrice(calculatedPrice);
        return priceLookUpDto;
    }

    @Async
    public void savePriceLookUpAsync(PriceLookUpDto priceLookUpDto) {
        PriceLookUp priceLookUp = new PriceLookUp();
        priceLookUp.setArea(priceLookUpDto.getArea());
        priceLookUp.setDistrictPrice(new DistrictPrice(priceLookUpDto.getDistrictPriceId()));
        priceLookUp.setLookUpDate(priceLookUpDto.getLookUpDate());
        priceLookUpRepository.save(priceLookUp);
    }

    @Transactional(readOnly = true)
    public Page<PriceLookUpDto> getPriceLookUpHistory(Pageable pageable) {
        return priceLookUpRepository.findAll(pageable)
                .map(e -> modelMapper.map(e, PriceLookUpDto.class));
    }
}
