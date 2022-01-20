	package com.bd.tpfinal.initializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.services.UserService;

@Component
public class ApplicationInitializer implements CommandLineRunner {
	@Autowired UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		if (userService.userCount() > 0 ) return;
		
		Address addr = new Address();
		addr.setName("Direccion Nombre");
		addr.setAddress("50 y 120");
		addr.setApartment(null);
		addr.setCoords( new float[]{ 30, 55 } );
		addr.setDescription("Primer direccion");
		List<Address> addressList = new ArrayList<Address>();
		addressList.add(addr);
		
		Client cli = new Client();
		cli.setActive(true);
		cli.setAddresses( addressList );
		cli.setDateOfBirth( LocalDate.of( 1980 , 1 , 5 ) );
		cli.setDateOfRegister( LocalDate.now() );
		cli.setEmail("un@email.com");
		cli.setName("Cliente Prueba");
		cli.setPassword("unaPass");
		cli.setUsername("cliente1");
		
		addr.setClient(cli);
		
		userService.addNewUser(cli);

		
		Address addr2 = new Address();
		addr2.setName("Direccion Nombre Cli 2");
		addr2.setAddress("1 y 115");
		addr2.setApartment(null);
		addr2.setCoords( new float[]{ 30, 55 } );
		addr2.setDescription("Segunda direccion");
		addressList = new ArrayList<Address>();
		addressList.add(addr2);
		
		Client cli2 = new Client();
		cli2.setActive(false);
		cli2.setAddresses( addressList );
		cli2.setDateOfBirth( LocalDate.of( 1983 , 5 , 20 ) );
		cli2.setDateOfRegister( LocalDate.now() );
		cli2.setEmail("dos@email.com");
		cli2.setName("Cliente Prueba 2");
		cli2.setPassword("unaPasssss");
		cli2.setUsername("cliente2");
		
		addr2.setClient(cli2);
		userService.addNewUser(cli2);
		
	}
	
}
