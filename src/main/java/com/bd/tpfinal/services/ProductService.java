package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> agregarProductoSupplier(Long product_id, Product product) throws Exception;
    Optional<Product> modificaProductoConHistorico(long product_id, Product product) throws Exception;
    Optional<Product> EliminaProducto(long id_product)  throws Exception ;
    List<Product> getProductsAndTypeBySupplierId(long supplier_id);
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, LocalDate from_date, LocalDate to_date);
    Optional<Product> findProduct(Long id);
    void guardar(Product currentProduct);
    void eliminarLogico(Product product);
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();
}
