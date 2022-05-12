package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.request.suppliers.CreateSupplierRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.services.SuppliersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("suppliers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE, RequestMethod.PATCH})
public class SupplierController extends BaseController{
    //TODO implementar las siguientes acciones:
    //Eliminar un producto de los ofrecidos por un proveedor *
    //Obtener los proveedores de un cierto tipo *
    //Obtener la informacion de los proveedores que tengan al menos una calificacion de 1 estrella. *
    //Obtener los proveedores que ofrezcan productos de todos los tipos *
    //Obtener los 10 proveedores con mas ordenes despachadas *

    private final SuppliersService supplierService;

    public SupplierController(SuppliersService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> retrieve(@RequestParam(value = "supplier_type", required = false) String supplierType,
                                                 @RequestParam(value = "product_type", required = false) String productType,
                                                 @RequestParam(value = "qualification_over", required = false) Float qualification){
        BaseResponse response = supplierService.retrieve(supplierType, productType, qualification);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @GetMapping("/qualified")
    public ResponseEntity<BaseResponse> qualification(){
        BaseResponse response = supplierService.getSupplierWithOneOrMoreStars();
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @GetMapping("/all_product_types")
    public ResponseEntity<BaseResponse> withAllProductTypes(){
        BaseResponse response = supplierService.findSuppliersWithAllProductTypes();
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @GetMapping("/ten_more_orders")
    public ResponseEntity<BaseResponse> with10OrdersAtLeast(){
        BaseResponse response = supplierService.getSupppliersWithAtLeast10DeliveredOrders();
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @DeleteMapping("/{supplier_id}/{product_id}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable("supplier_id") String supplierId, @PathVariable("product_id") String product_id) {
        BaseResponse response = supplierService.deleteSuppliersProduct(supplierId, product_id);
        return new ResponseEntity(response, responseStatus(response));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateSupplierRequest request){
        BaseResponse response = supplierService.create(request);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

}
