package com.bd.tpfinal.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name ="users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	
	@Column(length = 60, nullable = false)
    private String name;
	
	@Column(length = 60, unique = true, updatable = false, nullable = false)
    private String username;
	
	@Column(length = 20, nullable = false)
    private String password;
	
	@Column(unique = true, length = 40, nullable = false)
    private String email;
	
	@Column()
	private LocalDate dateOfBirth;
	
	@Column()
    private Boolean active;
	
	@Column()
    private Integer score;
    
	@Version
	private int version;
    
    public User(){ /*empty for framework */ }

      
	public User(String name, String username, String password, String email, LocalDate dateOfBirth) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.active = true;
		this.score = 0;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
    public void addScore(Integer scoreObtained) {
        this.score = this.score + scoreObtained;
    }

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
}
