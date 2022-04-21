package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long>
{

   //@Query(value= "Select * from Client where name = ?1", nativeQuery = true)
   Client findByName(@Param("name") String name);

    //métodos que ofrece CrudRepository:
    // https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html

}
