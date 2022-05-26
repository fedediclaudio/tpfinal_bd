package com.bd.tpfinal.controllers;

import com.bd.tpfinal.DTOs.ProductDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.services.HistoricalProductPriceService;
import com.bd.tpfinal.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value="/api/products")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HistoricalProductPriceService historicalProductPriceService;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todos los productos y su tipo de un proveedor especifico
    @GetMapping("/get-productos-tipos-por-supplier/{supplier_id}")
    public List<ProductDTO> getProductsAndTypeBySupplier(@PathVariable long supplier_id) {
        List<Product> products = productService.getProductsAndTypeBySupplierId(supplier_id);
        return products.stream()
                .map(product -> convertToDTO(product))
                .collect((Collectors.toList()));
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }


    //Obtener los precios de un producto entre dos fechas dadas
    // Hay que arreglar el tema del formato de fecha.
    @GetMapping("/get-precios-producto-dos-fechas/{product_id}")
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(@PathVariable long product_id,
                                                          @RequestParam(value = "start_date") Date start_date,
                                                          @RequestParam (value= "finish_date") Date finish_date) {
       return historicalProductPriceService.getPreciosProductoBetweenToFechas(product_id, start_date, finish_date);
    }
}
