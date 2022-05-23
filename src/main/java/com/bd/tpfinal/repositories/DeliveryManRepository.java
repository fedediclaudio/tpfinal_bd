package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryManRepository extends CrudRepository<DeliveryMan, Long> {
    public List<Client> findByUsernameIgnoreCaseContaining(String username);
    public List<Client> findByUsername(String username);
}
