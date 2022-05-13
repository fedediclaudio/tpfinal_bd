package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.dtos.common.SupplierWithOrdersCountDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.mappers.suppplier.SupplierMapper;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.model.SupplierWithOrdersCount;
import com.bd.tpfinal.proxy.repositories.SupplierRepositoryProxy;
import com.bd.tpfinal.repositories.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

public class SupplierRepositoryProxyImpl implements SupplierRepositoryProxy {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private  final SupplierWithOrdersCountRepository supplierWithOrdersCountRepository;
    private final SupplierTypeRepository supplierTypeRepository;
    public SupplierRepositoryProxyImpl(SupplierRepository supplierRepository, ProductTypeRepository productTypeRepository,
                                       ProductRepository productRepository, SupplierMapper supplierMapper,
                                       ProductMapper productMapper, SupplierWithOrdersCountRepository supplierWithOrdersCountRepository, SupplierTypeRepository supplierTypeRepository) {
        this.supplierRepository = supplierRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.supplierMapper = supplierMapper;
        this.productMapper = productMapper;
        this.supplierWithOrdersCountRepository = supplierWithOrdersCountRepository;
        this.supplierTypeRepository = supplierTypeRepository;
    }

    @Override
    public List<SupplierDto> findSuppliers(String supplierType, String productType, Float qualification) {
        List<Supplier> suppliers = supplierRepository.findAll();

        if (supplierType != null)
            suppliers = suppliers.stream().filter(supplier -> supplierType.equalsIgnoreCase(supplier.getType().getName())).collect(Collectors.toList());

        if (qualification != null)
            suppliers = suppliers.stream().filter(supplier -> supplier.getQualificationOfUsers() >= qualification).collect(Collectors.toList());

        if (productType != null) {
            Set<Product> products = Collections.synchronizedSet(new HashSet<>());

            suppliers.stream().forEach(supplier -> products.addAll(supplier.getProducts()));

            suppliers = products
                    .stream().distinct()
                    .filter(product -> productType.equalsIgnoreCase(product.getType().getName()))
                    .collect(Collectors.toList())
                    .stream()
                    .distinct()
                    .map(product -> product.getSupplier())
                    .collect(Collectors.toList());
        }
        return suppliers.stream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierDto> findSuppliersWithAllProductTypes() {
        long count = productTypeRepository.count();
        List<Supplier> suppliers = supplierRepository.findSuppliersWithAllProductTypes((int) count);
        return suppliers.stream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierDto> findByQualificationOfUsersGreaterThanEqual(float qualification) {
        List<Supplier> suppliers = supplierRepository.findSuppliersWithQualificationGreaterThanEquals(1f);
        return suppliers.stream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierWithOrdersCountDto> findSuppliersWith10OrdersAtLeast() {
        List<SupplierWithOrdersCount> suppliers = supplierWithOrdersCountRepository.suppliersAtList10Orders();
        if (suppliers.size() > 10)
            suppliers = suppliers.subList(0,10);

        return suppliers.stream()
                .map(supplier -> supplierMapper
                .toSupplierWithOrdersCountDto(supplier))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SupplierDto delete(String supplierId, String productId) throws PersistenceEntityException {
        Supplier supplier = supplierRepository.findById(IdConvertionHelper.convert(supplierId))
                .orElseThrow(() -> new PersistenceEntityException("Can't find supplier with id " + supplierId));

        Product product = supplier.getProducts().stream().filter(p -> p.getId().compareTo(IdConvertionHelper.convert(productId))==0).findFirst()
                .orElseThrow(() -> new PersistenceEntityException("Can't find product with id " + productId +" for supplier id " +supplierId));

        boolean remove = supplier.getProducts().remove(product);
        supplierRepository.save(supplier);

        SupplierDto dto =  (SupplierDto) supplierMapper.toSupplierDto(product.getSupplier());
        dto.setProducts(supplier.getProducts().stream().map(p -> productMapper.toProductDto(p)).collect(Collectors.toList()));

        return dto;
    }

    @Override
    public SupplierDto create(SupplierDto supplierDto) throws PersistenceEntityException {
        SupplierType type = supplierTypeRepository.findById(IdConvertionHelper.convert(supplierDto.getSupplierTypeId()))
                .orElseThrow(() -> new PersistenceEntityException("Cant find supplier type with id " + supplierDto.getSupplierTypeId()));
        Supplier supplier = new Supplier();
        supplier.setAddress(supplierDto.getAddress());
        supplier.setCoords(supplierDto.getCoords());
        supplier.setCuil(supplierDto.getCuil());
        supplier.setName(supplierDto.getName());
        supplier.setType(type);
        supplier.getType().add(supplier);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toSupplierDto(supplier);
    }


}
