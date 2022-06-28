package com.bd.tpfinal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	public Optional<Client> findByUsername(String aUsername);

}
