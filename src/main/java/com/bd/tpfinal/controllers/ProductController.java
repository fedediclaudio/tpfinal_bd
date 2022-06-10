package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductTypeAvgPrice_DTO;
import com.bd.tpfinal.services.HistoricalProductPriceService;
import com.bd.tpfinal.services.ProductService;
import com.bd.tpfinal.utils.NoExisteProductoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/product")
public class ProductController
{
    private final ProductService productService;
    private final HistoricalProductPriceService historicalProductPriceService;

    @Autowired
    public ProductController(ProductService productService, HistoricalProductPriceService historicalProductPriceService)
    {
        this.productService = productService;
        this.historicalProductPriceService = historicalProductPriceService;
    }

    //////  POST

    @PostMapping(value = "/new")
    public void addProduct(@RequestBody Product newProduct)
    {
        this.productService.newProduct(newProduct);
    }

    ////// PUT

    //4) Actualizar los datos de un producto. Tenga en cuenta que puede cambiar su precio.
    @PutMapping("/update/{id_producto}/updated/{updatedProduct}")
    public void actualizarDatosProducto(@PathVariable(value = "id_producto") Long id_producto, @RequestBody Product updatedProduct) throws NoExisteProductoException
    {
        this.productService.updateData(id_producto, updatedProduct);
    }

    //5) Eliminar un producto de los ofrecidos por un proveedor.
    @PutMapping("/delete/{id}")
    public void eliminarProducto(@PathVariable(value="id") Long id) throws NoExisteProductoException
    {
        this.productService.eliminarProductoById(id);
    }


    //////  GET

    //7) Obtener todos los productos y su tipo, de un proveedor específico.
    // tal vez haya que meter un DTO
    @GetMapping("/supplier/{id_supplier}")
    public List<Product> getBySupplierId(@PathVariable(value="id_supplier") Long id_supplier)
    {
        return this.productService.getBySupplierId(id_supplier);
    }

    //12) Obtener los precios de un producto entre dos fechas dadas.
    @GetMapping("/historicalPrices/{id_producto}")
    public List<HistoricalProductPrice> getHPPDesdeHasta(@PathVariable(value="id_producto") Long id_producto,
                                                         @RequestParam(name = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                         @RequestParam(name = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo)

    {
         return this.historicalProductPriceService.getPrices(id_producto, dateFrom, dateTo);
    }

    //13) Obtener el precio promedio de los productos de cada tipo, para todos los tipos.
    @GetMapping("/avgPrices")
    public List<ProductTypeAvgPrice_DTO> getAvgPriceForProductType()
    {
        return this.getAvgPriceForProductType();
    }

    @GetMapping("/all")
    public List<Product> getAll()
    {
        return this.productService.getAll();
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable Long id)
    {
        return this.productService.getProductById(id);
    }
}
