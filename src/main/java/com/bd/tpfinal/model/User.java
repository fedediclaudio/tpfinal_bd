package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public abstract class User {

    @Id
    private String id;

    private String name;

    private String username;

    private String password;

    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private boolean active;

    private int score = 0;

    public boolean isActive() {
        return active;
    }

    public boolean isValid() {
        if (name.isBlank())
            return false;
        if (username.isBlank())
            return false;
        if (password.isBlank())
            return false;
        if (email.isBlank())
            return false;
        if (dateOfBirth.isAfter(LocalDate.now()))
            return false;
        return score >= 0;
    }

}
