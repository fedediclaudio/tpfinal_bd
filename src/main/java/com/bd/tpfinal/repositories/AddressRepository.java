package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>
{
}
