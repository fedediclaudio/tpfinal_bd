package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("User")
@Data
public abstract class User {

    @Id
    private String id;

    private String name;

    private String username;

    private String password;

    private String email;

    private Date dateOfBirth;

    private boolean scrore;

    private int score;

}
