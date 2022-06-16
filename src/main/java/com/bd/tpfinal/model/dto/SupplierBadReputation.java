package com.bd.tpfinal.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierBadReputation {

    String name;
    float stars;
    Integer amountOfBadReputations;

}
