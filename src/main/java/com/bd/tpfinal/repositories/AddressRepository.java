package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
