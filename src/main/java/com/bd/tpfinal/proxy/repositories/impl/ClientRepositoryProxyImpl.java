package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.AddressDto;
import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.exceptions.persistence.EntityNotFoundException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.repositories.ClientRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryProxyImpl implements ClientRepositoryProxy {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    private final OrderMapper orderMapper;

    public ClientRepositoryProxyImpl(ClientRepository clientRepository, ClientMapper clientMapper, OrderMapper orderMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public ClientDto findById(String id) {
        Client client = clientRepository.findById(IdConvertionHelper.convert(id)).orElseThrow(() -> new EntityNotFoundException("Client with id "+ id +" not found"));
        ClientDto clientDto = clientMapper.toClientDto(client);
        clientDto.setOrders(client.getOrders().stream().map(order -> orderMapper.toOrderDto(order)).collect(Collectors.toList()));
        return getClientDto(client, client.getAddresses(), client.getOrders());
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        client.setDateOfBirth(clientDto.getBirthDate());
        client.setUsername(clientDto.getUsername());
        client.setDateOfRegister(new Date());
        client = clientRepository.save(client);

        return getClientDto(client, null, null);
    }

    @Override
    public ClientDto addAddress(String id, String name, String adr, String apartment, float[] coords, String description) {
        Client client = clientRepository.findById(IdConvertionHelper.convert(id))
                .orElseThrow(() -> new EntityNotFoundException("Client with id "+ id +" not found"));

        Address address = new Address();
        address.setAddress(adr);
        address.setApartment(apartment);
        address.setName(name);
        address.setDescription(description);
        address.setCoords(coords);
        address.setClient(client);

        client.add(address);
        client = clientRepository.save(client);

        return getClientDto(client, client.getAddresses(), client.getOrders());
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(client -> clientMapper.toClientDto(client)).collect(Collectors.toList());
    }


    private ClientDto getClientDto(Client client, List<Address> addresses, List<Order> orders){
        ClientDto clientDto = clientMapper.toClientDto(client);
        if (orders != null)
            clientDto.setOrders(client.getOrders().stream().map(order -> orderMapper.toOrderDto(order)).collect(Collectors.toList()));
        if (addresses != null)
            clientDto.setAddresses(client.getAddresses().stream().map(address -> {
                return AddressDto.builder()
                        .id(address.getId().toString())
                        .address(address.toString())
                        .build();
            }).collect(Collectors.toList()));
        return clientDto;
    }
}
