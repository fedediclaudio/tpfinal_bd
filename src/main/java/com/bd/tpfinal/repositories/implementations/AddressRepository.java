package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Address;

public interface AddressRepository extends MongoRepository<Address, String> {

}
