package com.bd.tpfinal.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


/* 
SINGLE_TABLE - Single table per class hierarchy
The SINGLE_TABLE strategy has the advantage of being simple. Loading entities requires querying only one table, 
with the discriminator column being used to determine the type of the entity. This simplicity also helps when 
manually inspecting or modifying the entities stored in the database.

A disadvantage of this strategy is that the single table becomes very large when there are a lot of classes in 
the hierarchy. Also, columns that are mapped to a subclass in the hierarchy should be nullable, which is especially 
annoying with large inheritance hierarchies. Finally, a change to any one class in the hierarchy requires the 
single table to be altered, making the SINGLE_TABLE strategy only suitable for small inheritance hierarchies.

TABLE_PER_CLASS - Table per concrete class
The TABLE_PER_CLASS strategy does not require columns to be made nullable, and results in a database schema that is 
relatively simple to understand. As a result it is also easy to inspect or modify manually.

A downside is that polymorphically loading entities requires a UNION of all the mapped tables, which may impact 
performance. Finally, the duplication of column corresponding to superclass fields causes the database design to not 
be normalized. This makes it hard to perform aggregate (SQL) queries on the duplicated columns. As such this strategy 
is best suited to wide, but not deep, inheritance hierarchies in which the superclass fields are not ones you want to 
query on.

JOINED - Table per class
The JOINED strategy gives you a nicely normalized database schema without any duplicate columns or unwanted nullable 
columns. As such it is best suited to large inheritance hierarchies, be the deep or wide.

This strategy does make the data harder to inspect or modify manually. Also, the JOIN operation needed to load 
entities can become a performance problem or a downright barrier to the size of your inheritance strategy. Also note 
that Hibernate does not correctly handle discriminator columns when using the JOINED strategy.

BTW, when using Hibernate proxies, be aware that lazily loading a class mapped with any of the three strategies 
above always returns a proxy that is an instanceof the superclass.
 
*/

@Entity
@Table(name = "default_user")
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user")
    private Long id;

    @Column(length = 50, nullable = false)
	private String name;

	@Column(length = 30, updatable = false, unique = true, nullable = false)
	private String username;

	@Column(length = 30, nullable = false)
	private String password;

	@Column(length = 80, unique = true, nullable = false)
	private String email;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;

	private boolean active;

	private int score = 0;

	
	public User() {}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", email="
				+ email + ", dateOfBirth=" + dateOfBirth + ", active=" + active + ", score=" + score + "]";
	}
	
	public boolean isValid() {
		if (name.isBlank()) return false;
		if (username.isBlank()) return false;
		if (password .isBlank()) return false;
		if (email.isBlank()) return false;
		if (dateOfBirth.isAfter( LocalDate.now() )) return false;
		if (score < 0) return false;
		
		return true;
	}
	
}
