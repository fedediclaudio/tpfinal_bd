package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    public List<Client> findByUsernameIgnoreCaseContaining(String username);
    public List<Client> findByUsername(String username);
}
