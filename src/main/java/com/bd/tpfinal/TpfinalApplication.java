package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TpfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpfinalApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(ProductTypeRepository productTypeRepository){
		return args -> {
			ProductType pt = new ProductType();
			productTypeRepository.save(pt);
		};
	}

}
