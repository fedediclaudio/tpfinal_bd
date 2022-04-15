package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.response.BaseResponseDto;
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
    public ResponseEntity<BaseResponseDto> retrieve(@RequestParam(value = "supplier_type", required = false) String supplierType,
                                                    @RequestParam(value = "product_type", required = false) String productType,
                                                    @RequestParam(value = "qualification_over", required = false) Float qualification){
        BaseResponseDto response = supplierService.retrieve(supplierType, productType, qualification);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/qualified")
    public ResponseEntity<BaseResponseDto> qualification(){
        BaseResponseDto response = supplierService.getSupplierWithOneOrMoreStars();
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @GetMapping("/all_product_types")
    public ResponseEntity<BaseResponseDto> withAllProductTypes(){
        BaseResponseDto response = supplierService.findSuppliersWithAllProductTypes();
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @GetMapping("/ten_more_orders")
    public ResponseEntity<BaseResponseDto> with10OrdersAtLeast(){
        BaseResponseDto response = supplierService.getSupppliersWithAtLeast10DeliveredOrders();
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @DeleteMapping("/{supplier_id}/{product_id}")
    public ResponseEntity<BaseResponseDto> deleteProduct(@PathVariable("supplier_id") String supplierId,@PathVariable("product_id") String product_id) {
        BaseResponseDto response = supplierService.deleteSuppliersProduct(supplierId, product_id);
        return new ResponseEntity(response, responseStatus(response));
    }

}
