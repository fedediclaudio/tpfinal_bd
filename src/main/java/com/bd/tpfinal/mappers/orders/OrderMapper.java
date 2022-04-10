package com.bd.tpfinal.mappers.orders;

import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
//            @Mapping(source = "number", target = "number"),
            @Mapping(source = "status" ,target = "status", qualifiedByName = "mapStatus"),
//            @Mapping(target = "deliveryMan", ignore = true),
            @Mapping(target = "client", ignore = true),
            @Mapping(target = "address", ignore = true),
            @Mapping(source="qualification.score", target = "qualificationScore"),
            @Mapping(source="qualification.commentary", target = "qualificationComments"),
    })
    OrderDto toOrderDto(Order order);

    @Named("mapStatus")
    default String mapStatus(OrderStatus status){
        return (status == null) ? null : status.getName();
    }
}
