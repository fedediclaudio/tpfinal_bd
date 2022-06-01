package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class User
{
    @Version
    @Column(name = "OBJ_VERSION")
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    protected Long id; //para que el id lo hereden los hijos, estará bien ??

    @Column(name = "name")
    protected String name;

    @Column(name = "username", updatable= false, nullable = false)
    private String username;

    @Column(name = "password", updatable= false, nullable = false)
    private String password;

    @Column(name = "email", updatable= false)
    private String email;

    @Column(name = "date_of_birth_user", updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    protected Date dateOfBirth;

    @Column(name = "active_user")
    private boolean active;

    @Column(name = "score")
    private int score;

    public User()
    {
    }

    public User(String name, String username, String password, String email, Date dateOfBirth)
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.active = true;
        this.score = 0;
    }

    public Long getId()
    {
        return id;
    }

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

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
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
