package com.bd.tpfinal.mappers.client;

import com.bd.tpfinal.dtos.common.ClientDto;
import com.bd.tpfinal.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mappings({
            @Mapping(source = "id", target = "id")
    })
    ClientDto toClientDto(Client client);


    Client toClient(ClientDto clientDto);
}
