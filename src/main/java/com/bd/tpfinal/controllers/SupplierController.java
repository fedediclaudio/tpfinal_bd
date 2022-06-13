package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.dto.SupplierBadReputation;
import com.bd.tpfinal.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/supplier")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @PostMapping("/")
    public String createSupplier(@RequestBody Supplier supplier) {
        try {
            supplier = supplierService.createNewSupplier(supplier);
            return (supplier != null) ? supplier.getId() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/")
    public List<Supplier> getSupplierList() {
        try {
            return supplierService.getSupplierList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/listOfType")
    public List<Supplier> getSupplierListFromType(@RequestParam(required = true, name = "idSupplierType") String idSupplierType) {
        try {
            return supplierService.getSupplierListFromType(idSupplierType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/getTopTen")
    public List<Supplier> getTopTen() {
        try {
            return supplierService.getTopTen();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/getSupplierWithAtLeastOneStar")
    public List<SupplierBadReputation> getSupplierWithAtLeastOneStar() {
        try {
            return supplierService.getSupplierWithAtLeastOneStar();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/getSupplierWhoOfferAllProducts")
    public List<Supplier> getSupplierWhoOfferAllProducts() {
        try {
            return supplierService.getSupplierWhoOfferAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
