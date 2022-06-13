package com.bd.tpfinal.services;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public boolean addNewUser(User user) throws Exception {
        userRepository.save(user);
        return true;
    }

    public long userCount() throws Exception {
        return this.userRepository.count();
    }

    public List<User> getAllUsers() throws Exception {
        return this.userRepository.findAll();
    }
}
