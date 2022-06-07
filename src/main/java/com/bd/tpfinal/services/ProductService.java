package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Product;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> agregarProductoSupplier(Long product_id, Product product) throws Exception;
    List<Product> getProductsAndTypeBySupplierId(long supplier_id);
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, LocalDateTime from_date, LocalDateTime to_date);
    Optional<Product> findProduct(Long id);
    void guardar(Product currentProduct);
    void eliminar(Product product);
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();
}