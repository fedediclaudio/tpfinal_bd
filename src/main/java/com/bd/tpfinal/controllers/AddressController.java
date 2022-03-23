package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    ////    POST

    @PostMapping(value = "/new")
    public void addAddress(@RequestBody Address newAddress)
    {
        this.addressService.addAddress(newAddress);
    }

    ////    GET

    @GetMapping("/all")
    public List<Address> getAll()
    {
        return this.addressService.getAll();
    }

    @GetMapping("/{id_user}")
    public List<Address> getAddressByIdUser(Long idUser)
    {
        return this.addressService.getAllByIdUser(idUser);
    }



}
