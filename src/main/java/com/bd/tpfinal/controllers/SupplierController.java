package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.Supplier_Order_DTO;
import com.bd.tpfinal.model.Supplier_Qualif_DTO;
import com.bd.tpfinal.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController
{
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService)
    {
        this.supplierService = supplierService;
    }

    //////  POST

    @PostMapping(value = "/new")
    public void addSupplier(@RequestBody Supplier newSupplier)
    {
        this.supplierService.newSupplier(newSupplier);
    }

    //////  GET

    //6) Obtener todos los proveedores de un cierto tipo.
    @GetMapping("/type/{id_type}")
    public List<Supplier> getSupplierBySupplierTypeId(@PathVariable(value="id_type") Long id_type)
    {
        return this.supplierService.getSupplierBySupplierTypeId(id_type);
    }

    //11) Obtener los diez proveedores que más órdenes despacharon.
    @GetMapping("/TopTenOrders")
    public List<Supplier_Order_DTO> getTopTenSupplierWithOrders()
    {
        return this.supplierService.getTopTenSupplierWithOrders();
    }

    //14) Obtener la información de los proveedores que tengan al menos una calificación de una estrella (la más baja).
    // Es necesario también el número de estas calificaciones que el proveedor posee.
    @GetMapping("/calificacion({valor}")
    List<Supplier_Qualif_DTO> getByQualification1(@PathVariable float valor)
    {
        return this.supplierService.getByQualification1(valor);
    }

    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.
    @GetMapping("/alltypes")
    List<Supplier> getSupplierWithAllTypes()
    {
        return this.supplierService.getSupplierWithAllTypes();
    }


    @GetMapping("/all")
    public List<Supplier> getAll()
    {
        return this.supplierService.getAll();
    }

    @GetMapping("/id/{id}")
    public Supplier getSupplierById(@PathVariable Long id)
    {
        return this.supplierService.getSupplierById(id);
    }
}
