package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryManRepository extends CrudRepository<DeliveryMan, Long> {
    public List<Client> findByUsernameIgnoreCaseContaining(String username);
    public List<DeliveryMan> findByUsername(String username);

    @Query(value = "select  * "
            + "from user "
            + "where user_type = 'DeliveryMan' "
            + "order by score desc "
            + "limit 10", nativeQuery = true)
    public List<DeliveryMan> getTop10RepartidoresMayorPuntaje();

    @Query(value= "SELECT * FROM user "
            + "where user_type= 'DeliveryMan'"
            + " and active = true and free = true LIMIT 1", nativeQuery = true)
    public Optional<DeliveryMan> findByFreeIsTrue();
}
