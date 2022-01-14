package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.User;

public interface UserService {
	
	boolean addNewUser(User user);
	
	long userCount();
	
	List<User> getAllUsers();
}
