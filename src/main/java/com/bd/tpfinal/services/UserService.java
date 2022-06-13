package com.bd.tpfinal.services;

import com.bd.tpfinal.model.User;

import java.util.List;

public interface UserService {
	
	boolean addNewUser(User user) throws Exception;
	long userCount() throws Exception;
	List<User> getAllUsers() throws Exception;
	
}
