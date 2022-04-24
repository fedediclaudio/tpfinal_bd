package com.bd.tpfinal.dtos.request.delivery;

import javax.validation.constraints.Email;

public class CreateDeliveryManRequest {
    private String name;
    private String username;
    private String password;
    @Email
    private String email;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
