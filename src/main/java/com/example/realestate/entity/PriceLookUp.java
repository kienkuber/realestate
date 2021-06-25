package com.example.realestate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "price_look_up")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PriceLookUp {

    @Id
    @Column(name = "price_look_up_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area")
    @NotNull
    private Float area;

    @ManyToOne(optional = false)
    @JoinColumn(name = "district_price_id", referencedColumnName = "district_price_id", nullable = false)
    @NotNull
    private DistrictPrice districtPrice;

    @Column(name = "look_up_date")
    private Date lookUpDate;
}
