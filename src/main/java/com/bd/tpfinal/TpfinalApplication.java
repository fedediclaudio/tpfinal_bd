package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.logging.Logger;

@SpringBootApplication
public class TpfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpfinalApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(ProductTypeRepository productTypeRepository){
		return args -> {

			ProductType productType_1 = new ProductType();
			productType_1.setName("Almacen");
			productType_1.setDescription("Productos almacen");

			ProductType productType_2 = new ProductType();
			productType_2.setName("Limpieza");
			productType_2.setDescription("Productos limpieza");

			ProductType productType_3 = new ProductType();
			productType_3.setName("Verduleria");
			productType_3.setDescription("Productos verduleria");

			ProductType productType_5 = new ProductType();
			productType_5.setName("Panificados");
			productType_5.setDescription("Productos de panadería");

			ProductType productType_6 = new ProductType();
			productType_6.setName("Café");
			productType_6.setDescription("Productos de café");

			//---------------
			ProductType productType_7 = new ProductType();
			productType_7.setName("Muebles de interior");
			productType_7.setDescription("Productos de mueblería");

			ProductType productType_15 = new ProductType();
			productType_15.setName("Muebles de exterior");
			productType_15.setDescription("Productos de mueblería");

			//-------------------------------
			ProductType productType_8 = new ProductType();
			productType_8.setName("Aire libre y jardín");
			productType_8.setDescription("Productos para Aire libre y jardín");

			ProductType productType_10 = new ProductType();
			productType_10.setName("Tiempo libre, deporte y entretenimiento");
			productType_10.setDescription("Productos para tiempo libre, deporte y entretenimiento");


			ProductType productType_4 = new ProductType();
			productType_4.setName("Autopartes");
			productType_4.setDescription("Productos autopartes");


			//--------------------------
			ProductType productType_9 = new ProductType();
			productType_9.setName("Herramientas");
			productType_9.setDescription("Productos para herramientas");

			ProductType productType_11 = new ProductType();
			productType_11.setName("Construccion");
			productType_11.setDescription("Productos para construccion");

			//----------------------------
			ProductType productType_12 = new ProductType();
			productType_12.setName("Indumentaria y moda");
			productType_12.setDescription("Productos para indumentaria y moda");

			//----------------
			ProductType productType_13 = new ProductType();
			productType_13.setName("Alimento para animales");
			productType_13.setDescription("Productos para mascotas");

			ProductType productType_14 = new ProductType();
			productType_14.setName("Casas para animales");
			productType_14.setDescription("Productos para mascotas");

			productTypeRepository.saveAll(Arrays.asList(productType_1, productType_2, productType_3,
					productType_4, productType_5, productType_6, productType_7, productType_8, productType_9,
					productType_10, productType_11, productType_12, productType_13 , productType_14, productType_15));


		};
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
}
