package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

import java.util.List;

public interface ClientRepositoryProxy {
    ClientDto findById(String id) throws PersistenceEntityException;

    ClientDto create(ClientDto clientDto) throws PersistenceEntityException;

    ClientDto addAddress(String id, String name, String address, String apartment, float[] coords, String description) throws PersistenceEntityException;

    List<ClientDto> findAll();
}
