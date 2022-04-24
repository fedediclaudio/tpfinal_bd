package com.bd.tpfinal.mappers.product;

import com.bd.tpfinal.dtos.common.HistoricalPriceDto;
import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "id", target="productId"),
            @Mapping(source = "name", target="productName"),
            @Mapping(source = "description", target="productDescription"),
            @Mapping(source = "supplier.id", target="supplierId"),
            @Mapping(source = "supplier.name", target="supplierName"),
            @Mapping(source = "type.name", target="productType"),
            @Mapping(source = "type.id", target="productTypeId"),
            @Mapping(source = "price", target="price"),
            @Mapping(source = "weight", target = "weight"),
            @Mapping(target = "prices", ignore = true)
    })
    ProductDto toProductDto(Product product);




    @Mappings({
            @Mapping(source = "id", target="productId"),
            @Mapping(source = "name", target="productName"),
            @Mapping(source = "description", target="productDescription"),
            @Mapping(source = "supplier.id", target="supplierId"),
            @Mapping(source = "supplier.name", target="supplierName"),
            @Mapping(source = "type.name", target="productType"),
            @Mapping(source = "type.id", target="productTypeId"),
            @Mapping(source = "price", target="price"),
            @Mapping(source = "weight", target = "weight"),
            @Mapping(source = "prices", target = "prices", qualifiedByName = "mapPrices")
    })
    ProductDto toProductDtoWithPrices(Product product);

    @Named("mapPrices")
    default List<HistoricalPriceDto> mapPrices(List<HistoricalProductPrice> prices){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (prices == null || prices.size() == 0)
            return new ArrayList<>();
        return prices.stream().map(price ->
        {
            HistoricalPriceDto priceDto = HistoricalPriceDto.builder()
                    .id(price.getId().toString())
                    .price(price.getPrice())
                    .from(price.getStartDate() != null ?  sdf.format(price.getStartDate()) : "")
                    .to(price.getFinishDate()  != null ? sdf.format(price.getFinishDate()) : "")
                    .build();
            return priceDto;
        }).collect(Collectors.toList());
    }
}
