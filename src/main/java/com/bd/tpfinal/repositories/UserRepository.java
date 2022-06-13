package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User getUserByUsernameAndPassword(String username, String password);

    User getUserByUsernameAndPasswordAndActive(String username, String password, Boolean active);

}
