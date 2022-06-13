package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address addNewAddress(Address address) throws Exception {
        return addressRepository.save(address);
    }

}
