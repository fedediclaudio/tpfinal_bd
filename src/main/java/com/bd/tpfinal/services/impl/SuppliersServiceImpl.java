package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.dtos.common.SupplierWithOrdersCountDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.suppliers.SupplierResponseDto;
import com.bd.tpfinal.dtos.response.suppliers.ListSupplierResponseDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.SupplierRepositoryProxy;
import com.bd.tpfinal.services.SuppliersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    private final SupplierRepositoryProxy supplierRepositoryProxy;

    public SuppliersServiceImpl(SupplierRepositoryProxy supplierRepositoryProxy) {
        this.supplierRepositoryProxy = supplierRepositoryProxy;
    }

    @Override
    public BaseResponseDto getSupppliersWithAtLeast10DeliveredOrders() {
        ListSupplierResponseDto response = new ListSupplierResponseDto();
        List<SupplierWithOrdersCountDto> suppliers = supplierRepositoryProxy.findSuppliersWith10OrdersAtLeast();
        response.setData(suppliers);
        return response;
    }

    @Override
    public BaseResponseDto deleteSuppliersProduct(String productId, String supplierId) {
        SupplierResponseDto response = new SupplierResponseDto();
        try {
            SupplierDto supplierDto = supplierRepositoryProxy.delete(productId, supplierId);
            response.setData(supplierDto);
            response.setMessage("Product was removed");
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }

        return response;
    }

    @Override
    public BaseResponseDto retrieve(String supplierType, String productType, Float qualification) {
        ListSupplierResponseDto response = new ListSupplierResponseDto();
        List<SupplierDto> suppliers = supplierRepositoryProxy.findSuppliers(supplierType, productType, qualification);
        response.setData(suppliers);
        return response;
    }

    @Override
    public BaseResponseDto getSupplierWithOneOrMoreStars() {
        ListSupplierResponseDto response = new ListSupplierResponseDto();
        List<SupplierDto> suppliers = supplierRepositoryProxy.findByQualificationOfUsersGreaterThanEqual(1f);
        response.setData(suppliers);
        return response;
    }

    @Override
    public BaseResponseDto findSuppliersWithAllProductTypes() {
        ListSupplierResponseDto response = new ListSupplierResponseDto();
        response.setMessage("Suppliers with all product types");
        List<SupplierDto> suppliers = supplierRepositoryProxy.findSuppliersWithAllProductTypes();
        response.setData(suppliers);
        return response;

    }
}
