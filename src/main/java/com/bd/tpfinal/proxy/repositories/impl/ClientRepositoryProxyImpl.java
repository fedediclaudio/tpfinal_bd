package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.repositories.ClientRepository;

import java.util.Optional;

public class ClientRepositoryProxyImpl implements ClientRepositoryProxy {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientRepositoryProxyImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto findById(String id) throws PersistenceEntityException {
        Optional<Client> optionalClient = clientRepository.findById(IdConvertionHelper.convert(id));
        if (!optionalClient.isPresent())
            throw new PersistenceEntityException("Client with id "+ id +" not found");
        return clientMapper.toClientDto(optionalClient.get());
    }
}
