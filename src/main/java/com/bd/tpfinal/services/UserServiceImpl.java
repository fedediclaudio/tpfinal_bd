package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.implementations.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserRepository userRepository;
	
	@Transactional
	public boolean addNewUser(User user) {
		try {
			this.userRepository.save(user);			
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public long userCount() {
		try {
			return this.userRepository.count();			
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<User> getAllUsers() {
		try {
			return this.userRepository.findAll();			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
