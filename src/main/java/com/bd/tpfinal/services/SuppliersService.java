package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.response.BaseResponseDto;

public interface SuppliersService {

    BaseResponseDto getSupppliersWithAtLeast10DeliveredOrders();

    BaseResponseDto deleteSuppliersProduct(String productId, String supplierId);

    BaseResponseDto retrieve(String supplierType, String productType, Float qualification);

    BaseResponseDto getSupplierWithOneOrMoreStars();

    BaseResponseDto findSuppliersWithAllProductTypes();

}
