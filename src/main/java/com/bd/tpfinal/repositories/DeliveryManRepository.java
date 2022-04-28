package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long>
{
    String query = "Select dm from DeliveryMan dm where dm.active = true AND dm.free = true";
    @Query(value = query)
    List<DeliveryMan> findAllFree();
}
