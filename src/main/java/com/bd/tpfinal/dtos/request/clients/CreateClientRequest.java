package com.bd.tpfinal.dtos.request.clients;

import com.bd.tpfinal.dtos.common.AddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateClientRequest {

    private String name;
    private String email;
    private String username;
    private String password;
    @JsonProperty("birth_date")
    private String birthDate;
    private CreateAddressRequest address;
}
