package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClientService
{
    public void addClient(Client newClient);
    public List<Client> getAClientList();
    public List<Client> getAll();
    public Client getClient(String username, String password);
    public Client getClientByName(String name);
    public Optional<Client> getClientById(Long id);
}
