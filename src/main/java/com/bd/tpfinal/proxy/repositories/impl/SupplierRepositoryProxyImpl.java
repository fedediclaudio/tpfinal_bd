package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.SupplierDto;
import com.bd.tpfinal.dtos.common.SupplierWithOrdersCountDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.mappers.suppplier.SupplierMapper;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.proxy.repositories.SupplierRepositoryProxy;
import com.bd.tpfinal.repositories.*;


import java.util.*;
import java.util.stream.Collectors;

public class SupplierRepositoryProxyImpl implements SupplierRepositoryProxy {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SupplierTypeRepository supplierTypeRepository;
    private final OrderRepository orderRepository;
    public SupplierRepositoryProxyImpl(SupplierRepository supplierRepository, ProductTypeRepository productTypeRepository,
                                       ProductRepository productRepository, SupplierMapper supplierMapper,
                                       ProductMapper productMapper, SupplierTypeRepository supplierTypeRepository,
                                       OrderRepository orderRepository) {
        this.supplierRepository = supplierRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.supplierMapper = supplierMapper;
        this.productMapper = productMapper;
        this.supplierTypeRepository = supplierTypeRepository;
        this.orderRepository = orderRepository;
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

        Set<String> productTypeIds = productTypeRepository.findAll().stream().map(s -> s.getId()).collect(Collectors.toSet());
        List<Supplier> suppliers = supplierRepository.findAll();
        suppliers = suppliers.stream().filter(s -> s.getProducts().stream().map(p -> p.getType().getId()).distinct().collect(Collectors.toSet()).containsAll(productTypeIds)).collect(Collectors.toList());

        return suppliers.stream().map(supplier -> supplierMapper.toSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<SupplierDto> findByQualificationOfUsersGreaterThanEqual(float qualification) {
        List<Supplier> suppliers = supplierRepository.findByQualificationOfUsersGreaterThanEqual(1f);
        return suppliers.stream().map(supplier -> {
            List<Order> orders = orderRepository.findByQualification_ScoreAndItems_Product_Supplier_Id(1f, supplier.getId());
            SupplierDto supplierDto = supplierMapper.toSupplierDto(supplier);
            supplierDto.setOneStarQualificationCount(orders.size());
            return  supplierDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SupplierWithOrdersCountDto> findSuppliersWith10OrdersAtLeast() {

        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierWithOrdersCountDto> supplierWithOrdersCount = new ArrayList<>();
        suppliers.stream().forEach(supplier -> {
            List<Order> orders = orderRepository.findByItems_Product_Supplier_id(supplier.getId());
            if (orders.size()>=10) {
                SupplierWithOrdersCountDto dto = SupplierWithOrdersCountDto.builder()
                                .ordersCount(orders.size())
                                .supplierId(supplier.getId())
                                .qualificationOfUsers(supplier.getQualificationOfUsers())
                                .cuil(supplier.getCuil())
                                .supplierTypeId(supplier.getType().getId())
                                .supplierType(supplier.getType().getName())
                                .build();
                supplierWithOrdersCount.add(dto);
            }

        });

        Collections.sort(supplierWithOrdersCount, new Comparator<SupplierWithOrdersCountDto>() {
            @Override
            public int compare(SupplierWithOrdersCountDto o1, SupplierWithOrdersCountDto o2) {
                if (o1.getOrdersCount() > o2.getOrdersCount())
                    return -1;
                else
                    return 1;
            }
        });

        return supplierWithOrdersCount;
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

    @Override
    public SupplierDto findById(String supplierId) throws PersistenceEntityException {
        Supplier supplier = supplierRepository.findById(IdConvertionHelper.convert(supplierId))
                .orElseThrow(() -> new PersistenceEntityException("Can't find supplier with id: " + supplierId));
        return supplierMapper.toSupplierDto(supplier);
    }


}
