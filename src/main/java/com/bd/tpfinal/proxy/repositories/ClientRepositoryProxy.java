package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.ClientDto;

import java.util.List;

public interface ClientRepositoryProxy {
    ClientDto findById(String id);

    ClientDto create(ClientDto clientDto);

    ClientDto addAddress(String id, String name, String address, String apartment, float[] coords, String description);

    List<ClientDto> findAll();
}
