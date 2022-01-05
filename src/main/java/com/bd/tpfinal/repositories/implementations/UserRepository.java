package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.interfaces.IUserRepository;

public interface UserRepository extends JpaRepository<User, Long>, IUserRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	User getUserById(Long id);
	
	// Retorna el usuario a partir de un usuario y una contraseña
	User getUserByUsernameAndPassword(String username, String password);
	
	// Retorna el usuario a partir de un usuario, una contraseña y del flag si esta activo o no
	User getUserByUsernameAndPasswordAndActive(String username, String password, Boolean active);
	
}
