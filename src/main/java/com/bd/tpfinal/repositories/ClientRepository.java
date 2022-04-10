package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
