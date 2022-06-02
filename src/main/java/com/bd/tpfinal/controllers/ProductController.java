package com.bd.tpfinal.controllers;

import com.bd.tpfinal.DTOs.ProductDTO;
import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.services.HistoricalProductPriceService;
import com.bd.tpfinal.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
                                                          @RequestParam(value = "start_date") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDateTime start_date,
                                                          @RequestParam (value= "finish_date") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDateTime finish_date) {
       return historicalProductPriceService.getPreciosProductoBetweenToFechas(product_id, start_date, finish_date);
    }

    @GetMapping("/get-precios-promedio-productos-de-cada-tipo")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoPrecioPromedioDTO> getPrecioPromedioProductosTipo() {
        List<ProductoPrecioPromedioDTO> productosPrecioPromedioDTO = productService.getProductosPrecioPromedioDTO();
        return productosPrecioPromedioDTO;
    }

    @PutMapping("/update-producto/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProducto(@RequestBody Product newProduct, @PathVariable long product_id) {
        Optional<Product> productToUpdate = productService.findProduct(product_id);
        if(productToUpdate.isPresent()) {
            Product currentProduct = productToUpdate.get();
            if(currentProduct.getPrice() != newProduct.getPrice()) {
                HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice();
                historicalProductPrice.setPrice(currentProduct.getPrice());
                historicalProductPrice.setFinishDate(new Date());
                historicalProductPrice.setProduct(currentProduct);
                // historicalProductPrice.setStartDate(); --> cual seria la start date?
                currentProduct.setPrices(Arrays.asList(historicalProductPrice));
            }
            currentProduct.setType(newProduct.getType());
            currentProduct.setName(newProduct.getName());
            currentProduct.setDescription(newProduct.getDescription());
            currentProduct.setWeight(newProduct.getWeight());
            currentProduct.setSupplier(newProduct.getSupplier());
            currentProduct.setPrice(newProduct.getPrice());
            productService.guardar(currentProduct);
        }
    }

    @DeleteMapping("/remover-producto/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerProducto(@PathVariable long product_id) {
        Optional<Product> productToRemove = productService.findProduct(product_id);
        if(productToRemove.isPresent()) {
            productService.eliminar(productToRemove.get());
        }
    }
}
