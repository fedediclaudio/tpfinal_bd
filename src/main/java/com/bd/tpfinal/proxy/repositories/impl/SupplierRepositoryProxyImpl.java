package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.dtos.common.SupplierWithOrdersCountDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.mappers.suppplier.SupplierMapper;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierWithOrdersCount;
import com.bd.tpfinal.proxy.repositories.SupplierRepositoryProxy;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import com.bd.tpfinal.repositories.SupplierWithOrdersCountRepository;

import java.util.*;
import java.util.stream.Collectors;

public class SupplierRepositoryProxyImpl implements SupplierRepositoryProxy {

    private final SupplierRepository supplierRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final SupplierMapper supplierMapper;
    private final ProductMapper productMapper;
    private  final SupplierWithOrdersCountRepository supplierWithOrdersCountRepository;
    public SupplierRepositoryProxyImpl(SupplierRepository supplierRepository, ProductTypeRepository productTypeRepository,
                                       ProductRepository productRepository, SupplierMapper supplierMapper,
                                       ProductMapper productMapper, SupplierWithOrdersCountRepository supplierWithOrdersCountRepository) {
        this.supplierRepository = supplierRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.supplierMapper = supplierMapper;
        this.productMapper = productMapper;
        this.supplierWithOrdersCountRepository = supplierWithOrdersCountRepository;
    }

    @Override
    public List<SupplierDto> findSuppliers(String supplierType, String productType, Float qualification) {
        List<Supplier> suppliers = supplierRepository.findAll();

        if (supplierType != null)
            suppliers = suppliers.parallelStream().filter(supplier -> supplierType.equalsIgnoreCase(supplier.getType().getName())).collect(Collectors.toList());

        if (qualification != null)
            suppliers = suppliers.parallelStream().filter(supplier -> supplier.getQualificationOfUsers() >= qualification).collect(Collectors.toList());

        if (productType != null) {
            Set<Product> products = Collections.synchronizedSet(new HashSet<>());

            suppliers.parallelStream().forEach(supplier -> products.addAll(supplier.getProducts()));

            suppliers = products
                    .parallelStream().distinct()
                    .filter(product -> productType.equalsIgnoreCase(product.getType().getName()))
                    .collect(Collectors.toList())
                    .parallelStream()
                    .distinct()
                    .map(product -> product.getSupplier())
                    .collect(Collectors.toList());
        }
        return suppliers.parallelStream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierDto> findSuppliersWithAllProductTypes() {
        long count = productTypeRepository.count();
        //TODO hacer esto
        List<Supplier> suppliers = new ArrayList<>(); //supplierRepository.findSuppliersWithAllProductTypes((int) count);
        return suppliers.parallelStream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierDto> findByQualificationOfUsersGreaterThanEqual(float qualification) {
        List<Supplier> suppliers = supplierRepository.findByQualificationOfUsersGreaterThan(1f);
        return suppliers.parallelStream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierWithOrdersCountDto> findSuppliersWith10OrdersAtLeast() {
        
        //TODO hacer esto
//        List<SupplierWithOrdersCount> suppliers = supplierWithOrdersCountRepository.suppliersAtList10Orders();
//        if (suppliers.size() > 10)
//            suppliers = suppliers.subList(0,10);
//
//        return suppliers.parallelStream()
//                .map(supplier -> supplierMapper
//                .toSupplierWithOrdersCountDto(supplier))
//                .collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public SupplierDto delete(String supplierId, String productId) throws PersistenceEntityException {
        Supplier supplier = supplierRepository.findById(IdConvertionHelper.convert(supplierId))
                .orElseThrow(() -> new PersistenceEntityException("Can't find supplier with id " + supplierId));

        Product product = supplier.getProducts().stream().filter(p -> p.getId().compareTo(IdConvertionHelper.convert(productId))==0).findFirst()
                .orElseThrow(() -> new PersistenceEntityException("Can't find product with id " + productId +" for supplier id " +supplierId));

        boolean remove = supplier.getProducts().remove(product);
        supplierRepository.save(supplier);

        SupplierDto dto =  (SupplierDto) supplierMapper.toSupplierDto(product.getSupplier());
        dto.setProducts(supplier.getProducts().parallelStream().map(p -> productMapper.toProductDto(p)).collect(Collectors.toList()));

        return dto;
    }


}
