package com.bd.tpfinal.services;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService
{
    private final SupplierRepository supplierRepository;
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ProductTypeRepository productTypeRepository)
    {
        this.supplierRepository = supplierRepository;
        this.productTypeRepository = productTypeRepository;
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
    public List<Supplier_Qualif_DTO> getByQualification1(float valor)
    {
        Iterator<Supplier> suppliers = this.supplierRepository.findSuppliersQualif_1(valor).iterator();
        //Iterator<Supplier> suppliers = this.supplierRepository.findSuppliers_1(1F).iterator();
        //List<Supplier_Qualif_DTO> supplier_qualif_dtos = new ArrayList<Supplier_Qualif_DTO>();
        //Iterator<Supplier_Qualif_DTO> cuentas = this.supplierRepository.findCount().iterator();
        List<Supplier_Qualif_DTO> supplier_qualif_dtos = new ArrayList<Supplier_Qualif_DTO>();
        while(suppliers.hasNext())
        {
            Supplier supplier = suppliers.next();
            Supplier_Qualif_DTO supplier_qualif_dto = new Supplier_Qualif_DTO();
            supplier_qualif_dto.setId_supplier(supplier.getId());

            supplier_qualif_dtos.add(supplier_qualif_dto);
            int cuenta = this.supplierRepository.findCountBySupplierIdAndScoreOne(supplier.getId(), valor);
            supplier_qualif_dto.setCantidad_1(cuenta);
        }

        return supplier_qualif_dtos;

    }

    @Override
    @Transactional
    public List<Supplier> getSupplierWithAllTypes()
    {
        List<Supplier> suppliersRta = new ArrayList<Supplier>();
        long cant_Product_Types = this.productTypeRepository.findAll().size();
        Iterator<Supplier> suppliers = this.supplierRepository.findAll().iterator();
        while(suppliers.hasNext())
        {
            Supplier supplier = suppliers.next();
            long cantidad = supplier.getProducts().stream().map(Product::getType)
                    .map(ProductType::getName)
                    .distinct()
                    .count();
            if(cantidad == cant_Product_Types)
                suppliersRta.add(supplier);
        }
        return suppliersRta;
    }

    @Override
    @Transactional
    public List<Supplier_Order_DTO> getTopTenSupplierWithOrders()
    {

        return this.supplierRepository.findTopTenSupplierWithOrders(PageRequest.of(0, 10));
    }




}
