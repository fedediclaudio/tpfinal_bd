package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    /////    POST
    //@RequestBody: viene en formato de petición web transforma el jason que viene
    // de la petición y lo transforma al objeto Client
    @PostMapping(value = "/new")
    public void addClient(@RequestBody Client newClient)
    {
        this.clientService.newClient(newClient);
    }

    /////     GET

    @GetMapping("/all")
    public List<Client> getAll()
    {
        return this.clientService.getAll();
    }

    @GetMapping("/name/{name}")
    public Client getAllByName(@PathVariable String name)
    {
        return this.clientService.getClientByName(name);
    }

    //TODO: Ver esto de Optional
    @GetMapping("/id/{id}")
    public Optional<Client> getClientById(@PathVariable Long id)
    {
        return this.clientService.getClientById(id);
    }
}
