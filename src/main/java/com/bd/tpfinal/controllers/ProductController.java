package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.request.products.CreateProductRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
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



    @GetMapping("/{product_id}")
    public ResponseEntity<BaseResponse> getProduct(@PathVariable(value = "product_id") String productId){
        BaseResponse response;
        response = productsService.retrieve(productId);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getProducts(@RequestParam(value = "supplier_id", required = false) String supplierId,
                                                    @RequestParam(value = "product_id", required = false) String productId){
        BaseResponse response;
        if (productId != null && !productId.isEmpty()){
            response = productsService.retrieve(productId);
        } else if (supplierId == null || supplierId.isEmpty()){
            response = productsService.retrieve();
        } else {
            response = productsService.getProductsWithProductTypeBySupplier(supplierId);
        }
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<BaseResponse> update(@PathVariable("product_id") String productId, @RequestBody CreateProductRequest createProductRequest){
        BaseResponse response = productsService.update(productId, createProductRequest);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @GetMapping("/average")
    public ResponseEntity<BaseResponse> productsAveragePrice(){
        BaseResponse response = productsService.getAverageProductPriceByProductType();
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }

    @GetMapping("/{product_id}/prices_between_dates")
    public ResponseEntity<BaseResponse> productPriceBetweenDates(@PathVariable(value = "product_id", required = true) String productId,
                                                                 @RequestParam(value = "from_date", required = true) String fromDate,
                                                                 @RequestParam(value = "to_date", required = true) String toDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BaseResponse response = null;
        try {
            response = productsService.getProductPriceBetweenDates(productId, sdf.parse(fromDate), sdf.parse(toDate));
        } catch (ParseException e) {
            response.setMessage("Error converting dates");
            response.setStatus(ResponseStatus.ERROR);
        }

        return new ResponseEntity<>(response, responseStatus(response));
    }

    @PostMapping("/{supplier_id}")
    public ResponseEntity<BaseResponse> create(@PathVariable("supplier_id") String supplierId, @RequestBody CreateProductRequest createProductRequest){
        BaseResponse response = productsService.create(supplierId, createProductRequest);
        return new ResponseEntity<BaseResponse>(response, responseStatus(response));
    }
}
