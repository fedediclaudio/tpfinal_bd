package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Override
    @Transactional
    public Optional<Product> agregarProductoSupplier(String supplier_id, Product product) throws Exception {
        Optional<Supplier> supplier  = supplierRepository.findById(supplier_id);
        if(supplier.isPresent()) {
            Product newProduct = new Product();
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setDescription(product.getDescription());
            newProduct.setWeight(product.getWeight());
            Supplier supplierActual = supplier.get();
            newProduct.setSupplier(supplierActual);

            HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice(newProduct);
            historicalProductPriceRepository.save(historicalProductPrice);
            newProduct.setPrices(Arrays.asList(historicalProductPrice)); // lo agrego al historial
            productRepository.save(newProduct);
        } else {
            throw new Exception("El proveedor con id: " + supplier_id + " no existe");
        }
        return Optional.ofNullable(product);
    }

    @Override
    @Transactional
    /**
     * Refactorizar este método
     */
    public Optional<Product> modificaProductoConHistorico(String product_id, Product product) throws Exception {
        Optional<Product> productToUpdate = productRepository.findById(product_id);
        if (productToUpdate.isPresent()) {
            Product currentProduct = productToUpdate.get();
            if (!currentProduct.isActive()) {
                throw new Exception("El producto seleccionado no está disponible");
            }
            if (currentProduct.getPrice() != product.getPrice()) { //cambio el precio, dejar historico
                List<HistoricalProductPrice> historical = currentProduct.getPrices();
                int i = 0;
                while (!historical.isEmpty() && i < historical.size()) {
                    HistoricalProductPrice tmp = historical.get(i);
                    if (tmp.getFinishDate() == null) {
                        tmp.setFinishDate(Calendar.getInstance().getTime());
                        break;
                    } else {
                        i++;
                    }
                }
                currentProduct.setPrice(product.getPrice());
                HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice(currentProduct); //creo historial de precio nuevo
                historicalProductPriceRepository.save(historicalProductPrice);

                historical.add(historicalProductPrice);
                currentProduct.setPrices(historical);
            }
            currentProduct.setType(product.getType());
            currentProduct.setName(product.getName());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setWeight(product.getWeight());
            currentProduct.setPrice(product.getPrice());
            productRepository.save(currentProduct);
        }
        return Optional.ofNullable(product);
    }

    @Override
    @Transactional
    public Optional<Product> eliminaProducto(String id_product) throws Exception {
        Optional<Product> productToRemove = productRepository.findById(id_product);
        if (productToRemove.isPresent()) {
            Product currentProduct = productToRemove.get();
            if (!currentProduct.isActive()) {
                throw new Exception("La producto seleccionado no está disponible");
            }
            currentProduct.setActive(false);
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
    public List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO() {
        return productRepository.getProductosPrecioPromedioDTO();
    }
}
