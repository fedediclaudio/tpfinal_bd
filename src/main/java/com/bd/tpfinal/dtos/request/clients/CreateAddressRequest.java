package com.bd.tpfinal.dtos.request.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAddressRequest {
    private String name;
    private String address;
    private String apartment;
    private float[] coords;
    private String description;
}
