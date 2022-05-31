package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.format.DateTimeFormatter;

@Data
@Document
public  class User {
    @Id
    private String id;
    private String name;

    private String username;

    private String password;

    private String email;

    private DateTimeFormatter dateOfBirth;

    private boolean scrore;

    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DateTimeFormatter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateTimeFormatter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isScrore() {
        return scrore;
    }

    public void setScrore(boolean scrore) {
        this.scrore = scrore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User() {
        this.name = "fede";
        this.username = "lockix";
        this.password = "1234";
        this.email = "amandifederico@gmail.com";
        this.dateOfBirth = DateTimeFormatter.ofPattern("1985/06/28 12:00:00");;
        this.scrore = true;
        this.score = 9;
    }
}
