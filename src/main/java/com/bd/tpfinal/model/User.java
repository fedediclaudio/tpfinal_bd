package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {


    @Id
    private ObjectId id;

    private String name;

    private String username;

    private String password;

    private String email;

    private Date dateOfBirth;

    private boolean active;

    private int score;

    @Version
    private Long version;

}
