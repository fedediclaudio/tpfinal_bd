package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

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
