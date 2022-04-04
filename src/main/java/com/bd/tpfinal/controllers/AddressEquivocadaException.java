package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Address;

public class AddressEquivocadaException extends Exception
{
    Address address;

    public AddressEquivocadaException(Address address)
    {
        super("Error en la dirección asiganada a la orden");
        this.address = address;
    }

    public Address getAddress()
    {
        return this.address;
    }
}
