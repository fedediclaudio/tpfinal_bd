package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
}
