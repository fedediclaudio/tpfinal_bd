package com.bd.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	

}
