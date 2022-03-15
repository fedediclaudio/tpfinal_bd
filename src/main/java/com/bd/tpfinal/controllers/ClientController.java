package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController
{
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    //@RequestBody: viene en formato de petición web transforma el jason que viene
    // de la petición y lo transforma al objeto Client
    @PostMapping(value = "/")
    public void addClient(@RequestBody Client newClient)
    {
        this.clientService.addClient(newClient);
    }

    public List<Client> getAll()
    {
        return this.clientService.getAll();
    }

    //agregar un item a una orden ya creada
}
