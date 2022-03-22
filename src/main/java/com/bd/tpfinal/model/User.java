package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public abstract class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user", updatable = false, nullable = false)
    protected Long id; //para que el id lo hereden los hijos, estará bien ??

    @Column(name = "name_user", updatable= false, nullable = false)
    private String name;

    @Column(name = "username_user", updatable= false, nullable = false)
    private String username;

    @Column(name = "password_user", updatable= false, nullable = false)
    private String password;

    @Column(name = "email_user", updatable= false)
    private String email;

    @Column(name = "date_of_birth_user", updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    protected Date dateOfBirth;

    @Column(name = "scrore_user")
    private boolean scrore;

    @Column(name = "score")
    private int score;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isScrore()
    {
        return scrore;
    }

    public void setScrore(boolean scrore)
    {
        this.scrore = scrore;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}
