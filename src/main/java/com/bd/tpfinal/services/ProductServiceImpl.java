package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Optional<Product> agregarProductoSupplier(Long supplier_id, Product product) throws Exception{
        Optional<Supplier> supplier  = supplierRepository.findById(supplier_id);
        if(supplier.isPresent()) {
            Product newProduct = new Product();
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setDescription(product.getDescription());
            newProduct.setWeight(product.getWeight());

            Supplier supplierActual = supplier.get();
            newProduct.setSupplier(supplierActual);

            HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice(newProduct); //creo historial de precio nuevo
            newProduct.setPrices(Arrays.asList(historicalProductPrice)); // lo agrego al historial

            productRepository.save(newProduct);
        } else {
            throw new Exception("El proveedor con id: " + supplier_id + " no existe");
        }
        return Optional.ofNullable(product);
    }



    @Override
    public List<Product> getProductsAndTypeBySupplierId(long supplier_id) {
        return productRepository.findBySupplierId(supplier_id);
    }

    @Override
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, LocalDateTime start_date, LocalDateTime finish_date) {
       return historicalProductPriceRepository.findByProductIdAndStartDateGreaterThanAndFinishDateLessThan(product_id, start_date, finish_date);
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void guardar(Product currentProduct) {
        productRepository.save(currentProduct);
    }

    @Override
    public void eliminar(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO() {
        return productRepository.getProductosPrecioPromedioDTO();
    }

}
