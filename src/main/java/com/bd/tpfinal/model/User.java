package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {


    private String id;

    private String name;

    private String username;

    private String password;

    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private boolean active;

    private int score = 0;

    @Version
    private Long version;

}
