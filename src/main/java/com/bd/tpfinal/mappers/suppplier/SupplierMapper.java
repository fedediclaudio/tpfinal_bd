package com.bd.tpfinal.mappers.suppplier;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.model.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    @Mappings({
            @Mapping(source = "id", target = "supplierId"),
            @Mapping(source = "type.name", target = "supplierType"),
            @Mapping(source = "type.id", target = "supplierTypeId"),
            @Mapping(target = "products", ignore = true),

    })
    SupplierDto toSupplierDto(Supplier supplier);

}
