package com.bd.tpfinal.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.model.dto.SupplierBadReputation;
import com.bd.tpfinal.repositories.implementations.OrderRepository;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;
import com.bd.tpfinal.repositories.implementations.SupplierTypeRepository;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired OrderRepository orderRepository;
	@Autowired ProductTypeRepository productTypeRepository;
	@Autowired SupplierRepository supplierRepository;
	@Autowired SupplierTypeRepository supplierTypeRepository;

    public Supplier createNewSupplier(Supplier supplier) throws Exception {
        // Verifico todos los campos
        if (supplier.getName().isBlank()) return null;
        if (supplier.getCuil().isBlank()) return null;
        if (supplier.getAddress().isBlank()) return null;
        if (supplier.getCoords().length != 2) return null;
        if (supplier.getType() == null) return null;

        // Busco el SupplierType en la BD
        SupplierType supplierType = supplierTypeRepository.getSupplierTypeById(supplier.getType().getId());

        // Si el SupplierType no existe, retorno null
        if (supplierType == null) {
            System.out.println("El SupplierType no existe");
            return null;
        }

        // Lo sobreescribo para que el supplier tenga la referencia de la BD
        supplier.setType(supplierType);

        // Inicializo los otros atributos
        supplier.setId(null);
        supplier.setProducts(new ArrayList<>());
        supplier.setQualificationOfUsers(0);

        // Grabo el Supplier
        supplier = supplierRepository.save(supplier);

        supplierType.addSupplierToList(supplier);
        supplierTypeRepository.save(supplierType);

        return supplier;

    }

    public List<Supplier> getSupplierList() throws Exception {
        return supplierRepository.findAll();
    }

    public List<Supplier> getSupplierListFromType(String idSupplierType) throws Exception {
        return supplierTypeRepository.findById(idSupplierType).get().getSuppliers();
    }

    public Map<Supplier, Integer> getTopTen() throws Exception {
        Map<Supplier, Integer> result = new HashMap<Supplier, Integer>();

        List<Supplier> suppliers = supplierRepository.findAll();

        suppliers.forEach(supplier -> {
            result.put(supplier, orderRepository.findByItems_Product_Supplier_Id(supplier.getId()).size());
        });

        return sortByValue(result);

    }

    public List<SupplierBadReputation> getSupplierWithAtLeastOneStar() throws Exception {
        List<SupplierBadReputation> badReputations = new ArrayList<>();
        List<Supplier> suppliers = supplierRepository.findByQualificationOfUsersGreaterThanEqual(1f);
        suppliers
                .forEach(supplier -> {
                    badReputations.add(new SupplierBadReputation(
                            supplier.getName(),
                            supplier.getQualificationOfUsers(),
                            orderRepository.findByQualification_ScoreAndItems_Product_Supplier_Id(1f, supplier.getId()).size()
                    ));
                });

        return badReputations;
    }

    
    private Map<Supplier, Integer> sortByValue(Map<Supplier, Integer> hm) {
        // Creo una lista con los elemenentos del HashMap
        List<Map.Entry<Supplier, Integer>> list = new LinkedList<Map.Entry<Supplier, Integer>>(hm.entrySet());

        // Ordeno la lista
        Collections.sort(list, new Comparator<Map.Entry<Supplier, Integer>>() {
            public int compare(Map.Entry<Supplier, Integer> o1,
                               Map.Entry<Supplier, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        
        // Pongo todos los datos del la lista ordenada en la HashMap
        Map<Supplier, Integer> temp = new LinkedHashMap<Supplier, Integer>();
        for (Map.Entry<Supplier, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
        // Retorno el Top10
        return temp.entrySet().stream().limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));
    }
    
    
    public List<Supplier> getSupplierWhoOfferAllProducts() throws Exception {
        // Lista que va a contener todos los Suppliers que ofrecen productos de todos los tipos
        List<Supplier> offer = new ArrayList<Supplier>();

        long productTypes = productTypeRepository.findAll().size();
        List<Supplier> allSuppliers = supplierRepository.findAll();

        allSuppliers.forEach(supplier -> {
            long amount = supplier.getProducts()
                    .stream()
                    .map(Product::getType)
                    .map(ProductType::getName)
                    .distinct()
                    .count();
            if(productTypes == amount){
                offer.add(supplier);
            }
        });

        return offer;
    }
    
}
