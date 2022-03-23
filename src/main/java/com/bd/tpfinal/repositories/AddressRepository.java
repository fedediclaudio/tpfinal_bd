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
    @Query(value= "Select * from address where user_id = ?1", nativeQuery = true)
    List<Address> findByIdUser(@Param("client") Long idUser);
}
