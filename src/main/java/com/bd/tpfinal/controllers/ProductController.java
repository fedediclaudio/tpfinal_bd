package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.request.products.CreateProductRequest;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.services.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public ResponseEntity<BaseResponseDto> update(@PathVariable("product_id") String productId, @RequestBody CreateProductRequest createProductRequest){
        BaseResponseDto response = productsService.update(productId, createProductRequest);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/average")
    public ResponseEntity<BaseResponseDto> productsAveragePrice(){
        BaseResponseDto response = productsService.getAverageProductPriceByProductType();
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }

    @GetMapping("/{product_id}/prices_between_dates")
    public ResponseEntity<BaseResponseDto> productPriceBetweenDates(@PathVariable(value = "product_id", required = true) String productId,
                                                                       @RequestParam(value = "from_date", required = true) String fromDate,
                                                                       @RequestParam(value = "to_date", required = true) String toDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BaseResponseDto response = null;
        try {
            response = productsService.getProductPriceBetweenDates(productId, sdf.parse(fromDate), sdf.parse(toDate));
        } catch (ParseException e) {
            response.setMessage("Error converting dates");
            response.setStatus(ResponseStatus.ERROR);
        }

        return new ResponseEntity<>(response, responseStatus(response));
    }

    @PostMapping("/{supplier_id}")
    public ResponseEntity<BaseResponseDto> create(@PathVariable("supplier_id") String supplierId, @RequestBody CreateProductRequest createProductRequest){
        BaseResponseDto response = productsService.create(supplierId, createProductRequest);
        return new ResponseEntity<BaseResponseDto>(response, responseStatus(response));
    }
}
