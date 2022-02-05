package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.User;

public interface UserService {
	
	boolean addNewUser(User user) throws Exception;
	long userCount() throws Exception;
	List<User> getAllUsers() throws Exception;
	
}
