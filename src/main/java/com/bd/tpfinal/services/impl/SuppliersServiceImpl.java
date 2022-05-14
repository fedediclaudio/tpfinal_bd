package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.dtos.common.SupplierWithOrdersCountDto;
import com.bd.tpfinal.dtos.request.suppliers.CreateSupplierRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.suppliers.SingleSupplierResponse;
import com.bd.tpfinal.dtos.response.suppliers.SupplierResponse;
import com.bd.tpfinal.dtos.response.suppliers.ListSupplierResponse;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.model.SupplierType;
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
    public BaseResponse getSupppliersWithAtLeast10DeliveredOrders() {
        ListSupplierResponse response = new ListSupplierResponse();
        List<SupplierWithOrdersCountDto> suppliers = supplierRepositoryProxy.findSuppliersWith10OrdersAtLeast();
        response.setMessage("Suppliers with at least 10 delivered orders.");
        response.setData(suppliers);
        return response;
    }

    @Override
    public BaseResponse deleteSuppliersProduct(String productId, String supplierId) {
        SupplierResponse response = new SupplierResponse();
        try {
            SupplierDto supplierDto = supplierRepositoryProxy.delete(productId, supplierId);
            response.setData(supplierDto);
            response.setMessage("Product id '" + productId + "'was removed from supplier id " + supplierId);
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }

        return response;
    }

    @Override
    public BaseResponse retrieve(String supplierType, String productType, Float qualification) {
        ListSupplierResponse response = new ListSupplierResponse();
        List<SupplierDto> suppliers = supplierRepositoryProxy.findSuppliers(supplierType, productType, qualification);
        response.setData(suppliers);
        response.setMessage("Suppliers found.");
        return response;
    }

    @Override
    public BaseResponse getSupplierWithOneOrMoreStars() {
        ListSupplierResponse response = new ListSupplierResponse();
        List<SupplierDto> suppliers = supplierRepositoryProxy.findByQualificationOfUsersGreaterThanEqual(1f);
        response.setData(suppliers);
        response.setMessage("Suppliers with one or more stars.");
        return response;
    }

    @Override
    public BaseResponse findSuppliersWithAllProductTypes() {
        ListSupplierResponse response = new ListSupplierResponse();
        response.setMessage("Suppliers with all product types");
        List<SupplierDto> suppliers = supplierRepositoryProxy.findSuppliersWithAllProductTypes();
        response.setData(suppliers);
        return response;

    }

    @Override
    public BaseResponse create(CreateSupplierRequest request) {
        BaseResponse response = new SingleSupplierResponse();
        SupplierDto supplierDto = new SupplierDto(null, request.getName(), request.getCuil(), request.getCoords(),
                request.getAddress(), 0, null, request.getSupplierTypeId(), null);
        try {
            supplierDto = supplierRepositoryProxy.create(supplierDto);
            response.setData(supplierDto);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
            e.printStackTrace();
        }
        return response;
    }
}
