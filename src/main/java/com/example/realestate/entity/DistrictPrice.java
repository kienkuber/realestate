package com.example.realestate.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "district_price")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DistrictPrice {

    @Id
    @Column(name = "district_price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price_per_square_meter")
    private Long pricePerSquareMeter;

    public DistrictPrice(Long id) {
        this.id = id;
    }
}