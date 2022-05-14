package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties("password")
public class ClientDto {
    @JsonProperty("client_id")
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    @JsonProperty("register_date")
    private Date registerDate;
    @JsonProperty("birth_date")
    private Date birthDate;
    private List<AddressDto> addresses = new ArrayList<>();
    private List<OrderDto> orders = new ArrayList<>();
}
