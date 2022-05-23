package com.bd.tpfinal.mappers.orders;

import com.bd.tpfinal.dtos.common.*;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface OrderMapper {

    @Mappings({
//            @Mapping(source = "number", target = "number"),
            @Mapping(source = "status" ,target = "status", qualifiedByName = "mapStatus"),
            @Mapping(source="deliveryMan", target = "deliveryMan", qualifiedByName = "mapDeliveryMan"),
            @Mapping(source = "client", target = "client", qualifiedByName = "mapClient"),
            @Mapping(source = "address", target = "address", qualifiedByName = "mapAddress"),
            @Mapping(source="qualification.score", target = "qualificationScore"),
            @Mapping(source="qualification.commentary", target = "qualificationComments"),
            @Mapping(source="items",target = "items", qualifiedByName = "mapItems"),
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

    @Named("mapItems")
    default List<ItemDto> mapItems(List<Item> items){
        if (items == null || items.isEmpty())
            return null;

        return items.stream().map(item -> {
                return ItemDto.builder()
                        .orderId(item.getOrder().getId().toString())
                        .itemId(item.getId().toString())
                        .productId(item.getProduct().getId().toString())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build();
            }).collect(Collectors.toList());

    }

    @Named("mapDeliveryMan")
    default DeliveryManDto mapDeliveryMan(DeliveryMan deliveryMan){
        if (deliveryMan == null)
            return null;
        DeliveryManDto dMan = DeliveryManDto.builder()
                .id(deliveryMan.getId().toString())
                .name(deliveryMan.getName())
                .score(deliveryMan.getScore())
                .numberOfSuccessOrders(deliveryMan.getNumberOfSuccessOrders())
                .free(deliveryMan.isFree())
                .build();
        return dMan;
    }


    @Named("mapClient")
    default ClientDto mapAddress(Client client){
        if (client == null)
            return null;
        ClientDto clientDto = ClientDto.builder()
                .id(client.getId().toString())
                .name(client.getName())
                .email(client.getEmail())
                .username(client.getUsername())
                .score(client.getScore())
                .build();
        return clientDto;
    }
}
