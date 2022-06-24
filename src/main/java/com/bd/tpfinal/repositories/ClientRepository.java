package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    @Query(value = "{username:{$eq:?0}},{name:1,_id:0}")
    Optional<Client> findClientByUsername(String username);
}
