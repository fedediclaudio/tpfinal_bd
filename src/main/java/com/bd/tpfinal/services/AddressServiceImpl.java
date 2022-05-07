package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService
{
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public void newAddress(Address newAddress)
    {
        this.addressRepository.save(newAddress);
    }

    @Override
    @Transactional
    public List<Address> getAll()
    {
        return addressRepository.findAll();
    }

    @Override
    @Transactional
    public List<Address> getAllByIdUser(Long id)
    {
        return this.addressRepository.findByIdUser(id);
    }


}
