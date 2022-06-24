package com.bd.tpfinal.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPrecioPromedioDTO {
    private String id;
    private float promedioPrecio;
}
