package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.repositories.interfaces.IHistoricalProductPriceRepository;

public interface HistoricalProductPriceRepository extends JpaRepository<HistoricalProductPrice, Long>, IHistoricalProductPriceRepository {
	
	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
}