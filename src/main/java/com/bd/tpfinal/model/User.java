package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;


import java.time.format.DateTimeFormatter;

@Data
public abstract class User {
    @Id
    private String id; //tipo de dato temporal
    private String name;

    private String username;

    private String password;

    private String email;

    private DateTimeFormatter dateOfBirth;

    private boolean scrore;

    private int score;
}
