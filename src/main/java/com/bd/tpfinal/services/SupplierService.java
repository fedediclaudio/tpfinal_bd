package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.dto.SupplierBadReputation;

import java.util.List;

public interface SupplierService {

    Supplier createNewSupplier(Supplier supplier) throws Exception;

    List<Supplier> getSupplierList() throws Exception;

    List<Supplier> getSupplierListFromType(String idSupplierType) throws Exception;

    List<Supplier> getTopTen() throws Exception;

    List<SupplierBadReputation> getSupplierWithAtLeastOneStar() throws Exception;

    List<Supplier> getSupplierWhoOfferAllProducts() throws Exception;
}
