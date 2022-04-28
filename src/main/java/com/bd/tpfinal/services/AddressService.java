package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService
{
    public void newAddress(Address newAddress);
    public List<Address> getAll();
    public List<Address> getAllByIdUser(Long idUser);
}
