package com.bd.tpfinal.controllers;

import com.bd.tpfinal.DTOs.ProductDTO;
import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.services.HistoricalProductPriceService;
import com.bd.tpfinal.services.ItemService;
import com.bd.tpfinal.services.OrderService;
import com.bd.tpfinal.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/api/products")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HistoricalProductPriceService historicalProductPriceService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    // Agregar un producto nuevo de un proveedor
    @PostMapping("/nuevo-product/{supplier_id}")
    public Product nuevoProduct(@RequestBody Product product, @PathVariable long supplier_id) throws Exception {
        try {
        Optional<Product> productoAgregado = productService.agregarProductoSupplier(supplier_id, product);
        if(productoAgregado.isPresent()) {
            return productoAgregado.get();
        }}
        catch (Exception e){}
        return null;
    }

    // Obtener todos los productos y su tipo de un proveedor especifico
    @GetMapping("/get-productos-tipos-por-supplier/{supplier_id}")
    @ResponseStatus(HttpStatus.OK)
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
    @GetMapping("/get-precios-producto-dos-fechas/{product_id}")
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(@PathVariable long product_id,
                                                          @RequestParam(value = "start_date") @DateTimeFormat(pattern="dd-MM-yyyy",
                                                                  iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
                                                          @RequestParam (value= "finish_date") @DateTimeFormat(pattern="dd-MM-yyyy",
                                                                  iso = DateTimeFormat.ISO.DATE) LocalDate finish_date) {
       return historicalProductPriceService.getPreciosProductoBetweenToFechas(product_id, start_date, finish_date);
    }

    @GetMapping("/get-precios-promedio-productos-de-cada-tipo")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoPrecioPromedioDTO> getPrecioPromedioProductosTipo() {
        List<ProductoPrecioPromedioDTO> productosPrecioPromedioDTO = productService.getProductosPrecioPromedioDTO();
        return productosPrecioPromedioDTO;
    }

    // actualiza los datos de un producto, si el precio cambia se deja historial del mismo
    @PutMapping("/update-producto/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Product updateProducto(@RequestBody Product newProduct, @PathVariable long product_id) throws Exception {
        try {
            Optional<Product> productoModificado = productService.modificaProductoConHistorico(product_id, newProduct);
            if(productoModificado.isPresent()) {
                return productoModificado.get();
            }

        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    @DeleteMapping("/remover-producto/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public Product removerProducto(@PathVariable long product_id) throws Exception {
        try {
            Optional<Product> productoModificado = productService.EliminaProducto(product_id);
            if (productoModificado.isPresent()) {
                return productoModificado.get();
            }
        }
        catch (Exception e) {}
        return null;
    }
}
