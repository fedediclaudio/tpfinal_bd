package com.bd.tpfinal.mappers.item;

import com.bd.tpfinal.dtos.common.ItemDto;
import com.bd.tpfinal.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mappings({
            @Mapping(source="id", target = "itemId"),
            @Mapping(source="order.id", target = "orderId"),
            @Mapping(source="product.id", target = "productId"),
            @Mapping(source="product.name", target = "productName"),
            @Mapping(source="quantity", target = "quantity"),

    })
    ItemDto toItemDto(Item item);
}
