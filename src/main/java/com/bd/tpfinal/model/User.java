package com.bd.tpfinal.model;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public abstract class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String username;

    private String password;

    private String email;

    private Date dateOfBirth;

    private int score;
}
