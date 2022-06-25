package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String> {
    public List<Client> findByUsernameIgnoreCaseContaining(String username);
    @Query(value = "{username:{$eq:?0}},{name:1,_id:0}")
    public List<DeliveryMan> findByUsername(String username);

    @Query(value = "find().sort({'score':-1}).limit(10)")//asi en mongodb
    public List<DeliveryMan> getTop10RepartidoresMayorPuntaje();

    @Query(value= "{free:{$eq:true}},{name:1,_id:1}") //devuelve los que estan libres, y sus datos nombre e id
    public Optional<DeliveryMan> findByFreeIsTrue();
}
