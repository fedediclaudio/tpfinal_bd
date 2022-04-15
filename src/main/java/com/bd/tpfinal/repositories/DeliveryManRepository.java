package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {
    Optional<DeliveryMan> findTopByFree(Boolean isFree);
}
