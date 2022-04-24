package com.bd.tpfinal.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public abstract class UserDto {
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;

}
