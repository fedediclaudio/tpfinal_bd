package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController
{
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService)
    {
        this.addressService = addressService;
    }

    @PostMapping(value = "/new")
    public void addAddress(@RequestBody Address newAddress)
    {
        this.addressService.addAddress(newAddress);
    }

    public List<Address> getAll()
    {
        return this.addressService.getAll();
    }

}
