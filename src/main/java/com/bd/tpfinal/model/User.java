package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {


    private String id;

    private String name;

    private String username;

    private String password;

    private String email;

    private Date dateOfBirth;

    private boolean active;

    private int score;

}
