package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Address;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, ObjectId> {
}
