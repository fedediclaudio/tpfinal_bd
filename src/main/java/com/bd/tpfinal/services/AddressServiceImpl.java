package com.bd.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.repositories.implementations.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired AddressRepository addressRepository;

	public Address addNewAddress(Address address) throws Exception {
		// Valido que la Address sea valido
		if (!address.isValid()) {
			System.out.println("La Address no es valida, corrobore los datos enviados");
			return null;
		}

		address = addressRepository.save( address );
		
		return address;
	}
	
}
