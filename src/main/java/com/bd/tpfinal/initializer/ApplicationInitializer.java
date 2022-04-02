	package com.bd.tpfinal.initializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.services.ClientService;
import com.bd.tpfinal.services.DeliveryManService;

@Component
public class ApplicationInitializer implements CommandLineRunner {
	@Autowired ClientService clientService;
	@Autowired DeliveryManService deliveryManService;
	
	@Override
	public void run(String... args) throws Exception {
		if (clientService.clientCount() > 0 ) return;
		
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
		
		clientService.addNewClient(cli);

		
		addr = new Address();
		addr.setName("Direccion Nombre Cli 2");
		addr.setAddress("1 y 115");
		addr.setApartment(null);
		addr.setCoords( new float[]{ 30, 55 } );
		addr.setDescription("Segunda direccion");
		addressList = new ArrayList<Address>();
		addressList.add(addr);
		
		cli = new Client();
		cli.setActive(false);
		cli.setAddresses( addressList );
		cli.setDateOfBirth( LocalDate.of( 1983 , 5 , 20 ) );
		cli.setDateOfRegister( LocalDate.now() );
		cli.setEmail("dos@email.com");
		cli.setName("Cliente Prueba 2");
		cli.setPassword("unaPasssss");
		cli.setUsername("cliente2");
		
		addr.setClient(cli);
		clientService.addNewClient(cli);
		
		
		DeliveryMan dm = new DeliveryMan();
		dm.setActive(true);
		dm.setDateOfBirth( LocalDate.of( 1989 , 12 , 11 ) );
		dm.setEmail("dm@email.com");
		dm.setName("Delivery Man Prueba 1");
		dm.setPassword("dmPass");
		dm.setUsername("dm1");
		
		deliveryManService.addNewDeliveryMan(dm);
		
	}
	
}
