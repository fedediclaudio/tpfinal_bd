package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService
{
    public void addClient(Client newClient);
    public List<Client> getAClientList();
    public List<Client> getAll();
    public Client getClient(String username, String password);
}
