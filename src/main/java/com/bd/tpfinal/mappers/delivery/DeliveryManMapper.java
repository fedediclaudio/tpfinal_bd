package com.bd.tpfinal.mappers.delivery;

import com.bd.tpfinal.dtos.common.*;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DeliveryManMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "numberOfSuccessOrders", target = "numberOfSuccessOrders"),
            @Mapping(source = "dateOfAdmission", target = "dateOfAdmission"),
            @Mapping(source = "free", target = "free"),
            @Mapping(source="pendingOrder",target = "pendingOrder", qualifiedByName = "mapPendingOrder")
    })
    DeliveryManDto toDeliveryManDto(DeliveryMan deliveryMan);


    @Mappings({
            @Mapping(source = "id", target = "id", qualifiedByName = "mapToId"),
            @Mapping(source = "numberOfSuccessOrders", target = "numberOfSuccessOrders"),
            @Mapping(source = "dateOfAdmission", target = "dateOfAdmission"),
            @Mapping(source = "free", target = "free"),
            @Mapping(target = "pendingOrder", ignore = true)
    })
    DeliveryMan toDeliveryMan(DeliveryManDto deliveryManDto);

    @Named("mapToId")
    default Long toId(String id){
        return id != null ? Long.parseLong(id) : null;
    }

    @Named("mapPendingOrder")
    default OrderDto toOrderDto(Order order){
        if (order == null)
            return null;

        return OrderDto.builder()
                .id(order.getId().toString())
                .status(order.getStatus().getName())
                .dateOfOrder(order.getDateOfOrder())
                .address(AddressDto.builder()
                        .id(order.getAddress().getId().toString())
                        .address(order.getAddress().toString())
                        .build())
                .totalPrice(order.getTotalPrice())
                .comments(order.getComments())
                .items(order.getItems().stream().map(item -> ItemDto.builder()
                        .itemId(item.getId().toString())
                        .productId(item.getProduct().getId().toString())
                        .price(item.getProduct().getPrice())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .build()
                ).collect(Collectors.toList()))
                .client(ClientDto.builder()
                        .id(order.getClient().getId().toString())
                        .name(order.getClient().getName())
                        .score(order.getClient().getScore())
                        .email(order.getClient().getEmail())
                        .build())
                .build();
    }

}
