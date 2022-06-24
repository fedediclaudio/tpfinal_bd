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

    @Autowired
    private OrderService orderService;

    @Override
    public Optional<Product> agregarProductoSupplier(Long product_id, Product product) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Product> modificaProductoConHistorico(long product_id, Product product) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Product> eliminaProducto(String id_product) throws Exception {
        Optional<Product> productToRemove = productRepository.findById(id_product);
        if(productToRemove.isPresent()) {
            Product currentProduct = productToRemove.get();
            if (!currentProduct.isActive()){
                throw new Exception("La producto seleccionado no está disponible");
            }
            currentProduct.setActive (false);
            productRepository.save(currentProduct);
            orderService.updateOrdersTotalPrice("Pending");
        }
        return Optional.ofNullable(productToRemove.get());
    }

    @Override
    public List<Product> getProductsAndTypeBySupplierId(String supplier_id) {
        return productRepository.findProductsBySupplierId(supplier_id);

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
        return productRepository.getProductosPrecioPromedioDTO();
    }
}
