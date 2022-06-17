package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.projections.ProductAndType;
import com.bd.tpfinal.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public String createProduct(@RequestBody Product product) {
        try {
            product = productService.createNewProduct(product);
            return (product != null) ? product.getId() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @PutMapping("/Query4")
    public boolean updateProduct(@RequestBody Product product) {
        try {
            return productService.updateProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PutMapping("/changePrice")
    public boolean changeProductPrice(@RequestParam(name = "idProduct") String idProduct, @RequestParam(name = "newPrice") float newPrice) {
        try {
            return productService.changeProductPrice(idProduct, newPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @DeleteMapping("/Query5")
    public boolean deleteProduct(@RequestParam(name = "idProduct") String idProduct) {
        try {
            return productService.deleteProduct(idProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/list")
    public List<Product> getProductList() {
        try {
            return productService.getProductList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getHistoricalPrices")
    public List<HistoricalProductPrice> getHistoricalPricesFromProduct(@RequestParam(name = "idProduct") String idProduct) {
        try {
            return productService.getHistoricalPricesFromProduct(idProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
            Obtener todos los productos y su tipo, de un proveedor específico.
     */
    @GetMapping("/Query7")
    public List<ProductAndType> getProductsFromSupplier(@RequestParam(name = "idSupplier") String idSupplier) {
        try {
            return productService.getProductsFromSupplier(idSupplier);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/Query12")
    public List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(@RequestParam(name = "dateFrom")
                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                   LocalDate dateFrom,
                                                                  @RequestParam(name = "dateTo")
                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                   LocalDate dateTo,
                                                                  @RequestParam(name = "idProduct")
                                                                                   String idProduct) {
        try {
            return productService.getHistoricalPricesBetweenTwoDates(dateFrom, dateTo, idProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
