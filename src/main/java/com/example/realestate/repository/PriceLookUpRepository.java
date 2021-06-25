package com.example.realestate.repository;

import com.example.realestate.entity.PriceLookUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceLookUpRepository extends JpaRepository<PriceLookUp, Long> {
}
