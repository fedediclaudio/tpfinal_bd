package com.bd.tpfinal.mappers.orders;

import com.bd.tpfinal.dtos.common.AddressDto;
import com.bd.tpfinal.dtos.common.ItemDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface OrderMapper {

    @Mappings({
//            @Mapping(source = "number", target = "number"),
            @Mapping(source = "status" ,target = "status", qualifiedByName = "mapStatus"),
//            @Mapping(target = "deliveryMan", ignore = true),
            @Mapping(target = "client", ignore = true),
            @Mapping(source = "address", target = "address", qualifiedByName = "mapAddress"),
            @Mapping(source="qualification.score", target = "qualificationScore"),
            @Mapping(source="qualification.commentary", target = "qualificationComments"),
            @Mapping(target = "items", ignore = true),
    })
    OrderDto toOrderDto(Order order);

    @Named("mapStatus")
    default String mapStatus(OrderStatus status){
        return (status == null) ? null : status.getName();
    }

    @Named("mapAddress")
    default AddressDto mapAddress(Address address){
        if (address == null)
            return null;
        AddressDto addressDto = new AddressDto(address.getId().toString(), address.toString());
        return addressDto;
    }
}
