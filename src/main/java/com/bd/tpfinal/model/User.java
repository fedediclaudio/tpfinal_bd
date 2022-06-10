package com.bd.tpfinal.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.format.DateTimeFormatter;

@Data
public abstract class User {
    @MongoId
    private ObjectId id;
    private String name;

    private String username;

    private String password;

    private String email;

    private DateTimeFormatter dateOfBirth;

    private boolean scrore;

    private int score;
}
