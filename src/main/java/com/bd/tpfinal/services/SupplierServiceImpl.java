package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.model.dto.SupplierBadReputation;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import com.bd.tpfinal.repositories.SupplierTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    public static final String BAD_SUPPLIER = "Supplier not exists";
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
        // Verifico todos los campos
        if (supplier.getName().isBlank()) return null;
        if (supplier.getCuil().isBlank()) return null;
        if (supplier.getAddress().isBlank()) return null;
        if (supplier.getCoords().length != 2) return null;
        if (supplier.getType() == null) return null;

        // Busco el SupplierType en la BD
        SupplierType supplierType = supplierTypeRepository
                .findById(supplier.getType().getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, BAD_SUPPLIER));

        // Si el SupplierType no existe, retorno null
        if (supplierType == null) {
            System.out.println("El SupplierType no existe");
            return null;
        }

        // Lo sobreescribo para que el supplier tenga la referencia de la BD
        supplier.setType(supplierType);

        // Inicializo los otros atributos
        supplier.setId(null);
        supplier.setProducts(null);
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
        return supplierRepository.getSupplierListFromType(idSupplierType);
    }

    public List<Supplier> getTopTen() throws Exception {
//		return supplierRepository.findTop10();
        return null;
    }

    public List<SupplierBadReputation> getSupplierWithAtLeastOneStar() throws Exception {
        List<SupplierBadReputation> badReputations = new ArrayList<>();
        List<Supplier> suppliers = supplierRepository.findByQualificationOfUsersGreaterThan(1f);
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

    private boolean hasAllSupplierTypes(List<Product> products, List<ProductType> types) {
        // Creo una copia de la los tipos de productos
        List<ProductType> actualTypes = new ArrayList<ProductType>();
        actualTypes.addAll(types);

        int idx = 0;
        while ((idx < products.size())) {
            Product actual = products.get(idx);
            // Elimino el tipo de producto de la lista de tipos
            actualTypes.removeIf(t -> t.getId() == actual.getType().getId());
            idx++;
        }
        // Retorno si la lista de tipos esta vacia (es decir, que tiene todos los tipos)
        return actualTypes.isEmpty();
    }

}
