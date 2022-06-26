package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> agregarProductoSupplier(String supplier_id, Product product) throws Exception;
    Optional<Product> modificaProductoConHistorico(String product_id, Product product) throws Exception;
    Optional<Product> eliminaProducto(String id_product)  throws Exception ;
    List<Product> getProductsAndTypeBySupplierId(String supplier_id);
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();
}
