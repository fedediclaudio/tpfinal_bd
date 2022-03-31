package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
    //@Query(value= "SELECT a FROM Address a WHERE a.client.id = :id")
    @Query(value= "SELECT a FROM Address a WHERE a.client.id = 1")
    List<Address> findByIdUser(@Param("id") Long id);
    //@Query(value= "SELECT a FROM address a WHERE a.client.id = ?1")
    //List<Address> findByIdUser(Long idUser);
}
