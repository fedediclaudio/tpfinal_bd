package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.clients.CreateAddressRequest;
import com.bd.tpfinal.dtos.request.clients.CreateClientRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;

public interface ClientsService {
    BaseResponse create(CreateClientRequest request);

    BaseResponse addAddress(String id, CreateAddressRequest request);

    BaseResponse retrieve(String id);

    BaseResponse retrieve();
}
