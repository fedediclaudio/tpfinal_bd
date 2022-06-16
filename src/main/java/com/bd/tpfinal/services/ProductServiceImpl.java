package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public Optional<Product> agregarProductoSupplier(Long product_id, Product product) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Product> modificaProductoConHistorico(long product_id, Product product) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Product> EliminaProducto(long id_product) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<Product> getProductsAndTypeBySupplierId(long supplier_id) {
        return productRepository.findBySupplierId(supplier_id);

    }

    @Override
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, LocalDate from_date, LocalDate to_date) {
        return null;
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return Optional.empty();
    }

    @Override
    public void guardar(Product currentProduct) {

    }

    @Override
    public void eliminarLogico(Product product) {

    }

    @Override
    public List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO() {
        return null;
    }
}
