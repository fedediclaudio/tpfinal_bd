package com.bd.tpfinal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.DeliveryMan;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long>{
	
	public Optional<DeliveryMan> findByUsername(String aUsername);

    public List<DeliveryMan> findByFreeTrueAndActiveTrue();
    
    public List<DeliveryMan> findFirst10ByActiveTrueOrderByScoreDesc(); //for getDiezMayorScore
}
