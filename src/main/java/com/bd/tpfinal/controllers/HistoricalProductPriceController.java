package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.services.HistoricalProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historicalProductPrice")
public class HistoricalProductPriceController
{
    private final HistoricalProductPriceService historicalProductPriceService;

    @Autowired
    public HistoricalProductPriceController(HistoricalProductPriceService historicalProductPriceService)
    {
        this.historicalProductPriceService = historicalProductPriceService;
    }

    //////  POST

    @PostMapping(value = "/new")
    public void addHistoricalProductPrice(@RequestBody HistoricalProductPrice newHistoricalProductPrice)
    {
        this.historicalProductPriceService.addHistoricalProductPrice(newHistoricalProductPrice);
    }

    //////  GET

    /**
     * @param
     * @return Listado de los HistoricalProductPrice
     */
    @GetMapping("/all")
    public List<HistoricalProductPrice> getAll()
    {
        return this.historicalProductPriceService.getAll(null);
    }

    /**
     * @param
     * @return Listado de los HistoricalProductPrice de un Producto.
     * Busca por product.id
     */
    @GetMapping("/all/productId/{productId}")
    public List<HistoricalProductPrice> getAll(@PathVariable Long productId)
    {
        return this.historicalProductPriceService.getAll(productId);
    }

    @GetMapping
    public HistoricalProductPrice getById(@PathVariable Long id)
    {
        return this.historicalProductPriceService.getById(id);
    }
}
