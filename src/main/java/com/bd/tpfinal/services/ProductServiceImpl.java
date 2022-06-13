package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;


    @Override
    @Transactional
    public Optional<Product> EliminaProducto(long id_product)  throws Exception {
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
    @Transactional
    public Optional<Product> modificaProductoConHistorico(long id_product,Product product)  throws Exception {
        Optional<Product> productToUpdate = productRepository.findById(id_product);
        if(productToUpdate.isPresent()) {
            Product currentProduct = productToUpdate.get();
            if (!currentProduct.isActive()){
                throw new Exception("La producto seleccionado no está disponible");
            }
            if(currentProduct.getPrice() != product.getPrice()) { //cambio el precio, dejar historico
                List<HistoricalProductPrice> historical = currentProduct.getPrices();
                int i =0;
                while (!historical.isEmpty()){
                    HistoricalProductPrice tmp = historical.get(i);
                    if (tmp.getFinishDate() == null)
                    {
                        tmp.setFinishDate(Calendar.getInstance().getTime() );
                        break;
                    }
                    else
                    { i++;}
                }
                currentProduct.setPrice(product.getPrice());
                HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice(currentProduct); //creo historial de precio nuevo
                historical.add(historicalProductPrice);
                currentProduct.setPrices(historical); // lo agrego al historial
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
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, LocalDate start_date, LocalDate finish_date) {
       return historicalProductPriceRepository.findHistoricalProductPricesBetweenTwoDates(product_id, start_date, finish_date);
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
    public void eliminarLogico(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO() {
        return productRepository.getProductosPrecioPromedioDTO();
    }

}
