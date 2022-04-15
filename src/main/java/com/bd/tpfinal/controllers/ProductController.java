package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.request.ProductRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ProductResponseDto;
import com.bd.tpfinal.services.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE, RequestMethod.PATCH})
public class ProductController extends BaseController {
    //TODO implementar las siguientes acciones:
    //Actualizar los datos de un producto
    //Obtener todos los productos, y su tipo, de un proveedor especifico
    //Obtener el precio promedio de los productos de cada tipo, para todos los tipos de producto.

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponseDto> getProducts(@RequestParam(value = "supplier_id", required = false) String supplierId){
        BaseResponseDto response;
        if (supplierId == null || supplierId.isEmpty()){
            response = productsService.retrieve();
        } else {
            response = productsService.getProductsWithProductTypeBySupplier(supplierId);
        }
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @PutMapping("/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponseDto> update(@PathVariable("product_id") String productId, @RequestBody ProductRequestDto productRequestDto){
        BaseResponseDto response = productsService.update(productId, productRequestDto);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/average")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponseDto> productsAveragePrice(){
        BaseResponseDto response = productsService.getAverageProductPriceByProductType();
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/{product_id}/prices_between_dates")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseDto> productPriceBetweenDates(@PathVariable("product_id") String productId,
                                                                       @RequestParam(value = "from_date")Date fromDate,
                                                                       @RequestParam(value = "to_date") Date toDate) {
        //TODO implement
        return null;
    }
}
