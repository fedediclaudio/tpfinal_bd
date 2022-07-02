package com.bd.tpfinal.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.Address;

@Repository
public interface AddressRepository extends MongoRepository<Address, ObjectId>{
	

}
