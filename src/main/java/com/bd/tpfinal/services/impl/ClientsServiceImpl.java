package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.request.clients.CreateAddressRequest;
import com.bd.tpfinal.dtos.request.clients.CreateClientRequest;
import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.client.ClientResponse;
import com.bd.tpfinal.dtos.response.client.ListClientResponse;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.services.ClientsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientsServiceImpl implements ClientsService {

    private final ClientRepositoryProxy clientRepositoryProxy;

    public ClientsServiceImpl(ClientRepositoryProxy clientRepositoryProxy) {
        this.clientRepositoryProxy = clientRepositoryProxy;
    }

    @Override
    public BaseResponse create(CreateClientRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ClientResponse response = new ClientResponse();

        try {
            List addresses = new ArrayList<>();
            ClientDto dto = ClientDto.builder()
                    .name(request.getName())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .birthDate(sdf.parse(request.getBirthDate()))
                    .build();
            dto = clientRepositoryProxy.create(dto);

            dto = this.createClientAddress(dto.getId(), request.getAddress());

            response.setData(dto);
            response.setMessage("Client created.");
        } catch (ParseException e) {
            response.setMessage("Error parsing date of birth.");
            response.setStatus(ResponseStatus.ERROR);
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }

        return response;
    }

    @Override
    public BaseResponse addAddress(String id, CreateAddressRequest request) {
        BaseResponse response = new ClientResponse();
        try {
            ClientDto clientDto = this.createClientAddress(id, request);
            response.setMessage("Address added to client.");
            response.setData(clientDto);
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }

    @Override
    public BaseResponse retrieve(String id){
        ClientResponse response = new ClientResponse();
        try {
            ClientDto clientDto = clientRepositoryProxy.findById(id);
            response.setMessage("Client found.");
            response.setData(clientDto);
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }

        return response;
    }

    @Override
    public BaseResponse retrieve() {
        List<ClientDto> clients = clientRepositoryProxy.findAll();
        ListClientResponse response = new ListClientResponse();
        return response;
    }

    private ClientDto createClientAddress(String id, CreateAddressRequest request) throws PersistenceEntityException {
            return clientRepositoryProxy.addAddress(id, request.getName(), request.getAddress(),
                    request.getApartment(), request.getCoords(), request.getDescription());
    }

}
