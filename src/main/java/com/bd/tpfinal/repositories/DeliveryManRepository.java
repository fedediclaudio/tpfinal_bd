package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {
    Optional<DeliveryMan> findTopByPendingOrderIsNull();

    List<DeliveryMan> findFirst10ByOrderByNumberOfSuccessOrdersDesc();

    List<DeliveryMan> findByPendingOrderIsNull();

    List<DeliveryMan> findFirst10ByOrderByScoreDesc();

    List<DeliveryMan> findFirst10ByOrderByScoreAsc();
}
