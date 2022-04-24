package com.bd.tpfinal.mappers.delivery;

import com.bd.tpfinal.dtos.common.DeliveryManDto;
import com.bd.tpfinal.model.DeliveryMan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DeliveryManMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "numberOfSuccessOrders", target = "numberOfSuccessOrders"),
            @Mapping(source = "dateOfAdmission", target = "dateOfAdmission"),
            @Mapping(source = "free", target = "free"),
            @Mapping(target = "ordersPending", ignore = true)
    })
    DeliveryManDto toDeliveryManDto(DeliveryMan deliveryMan);


    @Mappings({
            @Mapping(source = "id", target = "id", qualifiedByName = "mapToId"),
            @Mapping(source = "numberOfSuccessOrders", target = "numberOfSuccessOrders"),
            @Mapping(source = "dateOfAdmission", target = "dateOfAdmission"),
            @Mapping(source = "free", target = "free"),
            @Mapping(target = "ordersPending", ignore = true)
    })
    DeliveryMan toDeliveryMan(DeliveryManDto deliveryManDto);

    @Named("mapToId")
    default Long toId(String id){
        return id != null ? Long.parseLong(id) : null;
    }
}
