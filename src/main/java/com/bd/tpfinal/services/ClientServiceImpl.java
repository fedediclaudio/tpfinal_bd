package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService
{

    private final ClientRepository clientRepository;

    //Este constructor recibe un personRepository
    //PersonRepository es una interfaz que extiende de JpaRepository.
    //El framework se encarga de crear la clase que implementa ClientRepository y el objeto.

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    /**
     * solamente agrega un nuevo cliente cuando tiene un username diferente
     * No permito repetir username
     * @param newClient
     */
    public void addClient(Client newClient)
    {
        Client buscado = getClientByName(newClient.getUsername());
        if(buscado == null)
            clientRepository.save(newClient);
    }

    public List<Client> getAClientList()
    {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getAll()
    {
        return clientRepository.findAll();
    }

    //TODO:revisar esto
    @Override
    public Client getClient(String username, String password)
    {
        //return this.clientRepository.get;
        return (Client) this.clientRepository.findAll();
    }

    @Override
    public Client getClientByName(String name)
    {
        return this.clientRepository.findByName(name);
    }

    @Override
    public Optional<Client> getClientById(Long id)
    {
        return this.clientRepository.findById(id);
    }



}
