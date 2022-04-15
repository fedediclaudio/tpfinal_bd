package com.bd.tpfinal.mappers.product;

import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "id", target="productId"),
            @Mapping(source = "name", target="productName"),
            @Mapping(source = "supplier.id", target="supplierId"),
            @Mapping(source = "supplier.name", target="supplierName"),
            @Mapping(source = "type.name", target="productType"),
            @Mapping(source = "price", target="price")
    })
    ProductDto toProductDto(Product product);
}
