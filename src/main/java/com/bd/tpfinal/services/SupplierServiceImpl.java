package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.model.dto.SupplierBadReputation;
import com.bd.tpfinal.repositories.implementations.OrderRepository;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;
import com.bd.tpfinal.repositories.implementations.SupplierTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierTypeRepository supplierTypeRepository;
    private SupplierRepository supplierRepository;
    private OrderRepository orderRepository;
    private ProductTypeRepository productTypeRepository;

    @Autowired
    public SupplierServiceImpl(SupplierTypeRepository supplierTypeRepository, SupplierRepository supplierRepository
            , OrderRepository orderRepository, ProductTypeRepository productTypeRepository) {
        this.supplierTypeRepository = supplierTypeRepository;
        this.supplierRepository = supplierRepository;
        this.orderRepository = orderRepository;
        this.productTypeRepository = productTypeRepository;
    }

    public Supplier createNewSupplier(Supplier supplier) throws Exception {
        if (supplier.getName().isBlank()) return null;
        if (supplier.getCuil().isBlank()) return null;
        if (supplier.getAddress().isBlank()) return null;
        if (supplier.getCoords().length != 2) return null;
        if (supplier.getType() == null) return null;

        SupplierType supplierType = supplierTypeRepository.getSupplierTypeById(supplier.getType().getId());

        if (isNull(supplierType)) {
            System.out.println("El SupplierType no existe");
            return null;
        }

        supplier.setType(supplierType);

        supplier.setId(null);
        supplier.setProducts(new ArrayList<>());
        supplier.setQualificationOfUsers(0);

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
        List<Map.Entry<Supplier, Integer>> list = new LinkedList<Map.Entry<Supplier, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Supplier, Integer>>() {
            public int compare(Map.Entry<Supplier, Integer> o1,
                               Map.Entry<Supplier, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        Map<Supplier, Integer> temp = new LinkedHashMap<Supplier, Integer>();
        for (Map.Entry<Supplier, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        // Return the top 10
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
            if (productTypes == amount) {
                offer.add(supplier);
            }
        });

        return offer;
    }

}
