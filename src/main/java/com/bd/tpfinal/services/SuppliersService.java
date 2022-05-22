package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.suppliers.CreateSupplierRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;

public interface SuppliersService {

    BaseResponse getSupppliersWithAtLeast10DeliveredOrders();

    BaseResponse deleteSuppliersProduct(String productId, String supplierId);

    BaseResponse retrieve(String supplierType, String productType, Float qualification);

    BaseResponse getSupplierWithOneOrMoreStars();

    BaseResponse findSuppliersWithAllProductTypes();

    BaseResponse create(CreateSupplierRequest request);

    BaseResponse retrieve(String supplierId);
}
