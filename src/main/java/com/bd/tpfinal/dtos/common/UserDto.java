package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public abstract class UserDto {
    private String id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private String email;

}
