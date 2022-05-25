package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService
{
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository)
    {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public void newSupplier(Supplier newSupplier)
    {
        this.supplierRepository.save(newSupplier);
    }

    @Override
    @Transactional
    public List<Supplier> getAll()
    {
        return this.supplierRepository.findAll();
    }

    @Override
    @Transactional
    public Supplier getSupplierById(Long id)
    {
        return this.supplierRepository.getById(id);
    }

    @Override
    @Transactional
    public List<Supplier> getSupplierByName(String name)
    {
        return this.supplierRepository.findByName(name);
    }

    @Override
    @Transactional
    public List<Supplier> getSupplierBySupplierTypeId(Long id)
    {
        return this.supplierRepository.findBySupplierTypeId(id);
    }

    @Override
    @Transactional
    //11) Obtener los diez proveedores que más órdenes despacharon.
    public List<Supplier> getTop10Supplier()
    {
        return this.supplierRepository.findTopTenSupplier();
        //return this.supplierRepository.findSupplier();
    }

    @Override
    @Transactional
    public List<Order> getOrderBySupplier()
    {
        return this.supplierRepository.findOrderBYSupplier();
    }

    @Override
    @Transactional
    public List<Supplier> getByQualification1()
    {
        return this.supplierRepository.findByQualification1();
    }

    @Override
    @Transactional
    public List<Supplier> getSupplierWithAllTypes()
    {
        return this.supplierRepository.findSupplierWithAllTypes();
    }




}
