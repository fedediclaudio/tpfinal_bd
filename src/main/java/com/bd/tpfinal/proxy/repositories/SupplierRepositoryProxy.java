package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.dtos.common.SupplierWithOrdersCountDto;

import java.util.List;

public interface SupplierRepositoryProxy {

    List<SupplierDto> findSuppliers(String supplierType, String productType, Float qualification);

    List<SupplierDto> findByQualificationOfUsersGreaterThanEqual(float qualification);

    List<SupplierDto> findSuppliersWithAllProductTypes();

    List<SupplierWithOrdersCountDto> findSuppliersWith10OrdersAtLeast();

    SupplierDto delete(String supplierId, String productId);

    SupplierDto create(SupplierDto supplierDto);

    SupplierDto findById(String supplierId);
}
