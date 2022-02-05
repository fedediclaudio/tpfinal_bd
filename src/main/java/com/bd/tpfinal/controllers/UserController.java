package com.bd.tpfinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.services.UserService;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class UserController {
	@Autowired UserService userService;
		
	@GetMapping("/getUserCount")
    public long getUserCount() {
		try {
			return userService.userCount();			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
		try {
			return userService.getAllUsers();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

}
