package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.projections.ProductAndType;
import com.bd.tpfinal.repositories.implementations.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.implementations.ProductRepository;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    HistoricalProductPriceRepository historicalProductPriceRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public Product saveProduct(Product product) throws Exception {
        return productRepository.save(product);
    }

    public Product createNewProduct(Product product) throws Exception {
        if ((product.getName().isBlank()) ||
                (product.getDescription().isBlank()) ||
                (product.getPrice() < 0) ||
                (product.getWeight() < 0)) return null;

        if (isNull(product.getSupplier())) {
            System.out.println("Supplier no provisto");
            return null;
        }
        if (isNull(product.getType())) {
            System.out.println("ProductType no provisto");
            return null;
        }
        Supplier supplier = supplierRepository.getSupplierById(product.getSupplier().getId());
        if (isNull(supplier)) {
            System.out.println("El Supplier no existe");
            return null;
        }

        ProductType productType = productTypeRepository.getProductTypeById(product.getType().getId());
        if (isNull(productType)) {
            System.out.println("El ProductType no existe");
            return null;
        }

        product.setId(null);
        product.setSupplier(supplier);
        product.setType(productType);

        product = productRepository.save(product);

        HistoricalProductPrice hp = new HistoricalProductPrice(product);
        hp = historicalProductPriceRepository.save(hp);

        product.addHistoricalPrice(hp);
        product = productRepository.save(product);


        supplier.addProduct(product);
        productType.addProduct(product);
        supplierRepository.save(supplier);
        productTypeRepository.save(productType);

        return product;
    }

    public boolean updateProduct(Product product) throws Exception {
        if ((product.getId().isBlank()) ||
                (product.getName().isBlank()) ||
                (product.getDescription().isBlank()) ||
                (product.getPrice() < 0) ||
                (product.getWeight() < 0)) return false;

        // Corroboro que el producto exista
        Product dbProducto = productRepository.getProductById(product.getId());
        if (isNull(dbProducto)) {
            System.out.println("El producto no existe");
            return false;
        }
        if (dbProducto.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        if (dbProducto.getPrice() != product.getPrice())
            this.changeProductPrice(product.getId(), product.getPrice());

        dbProducto.setName(product.getName());
        dbProducto.setPrice(product.getPrice());
        dbProducto.setDescription(product.getDescription());
        dbProducto.setWeight(product.getWeight());

        productRepository.save(dbProducto);

        return true;
    }

    public boolean deleteProduct(String idProduct) throws Exception {
        Product product = productRepository.getProductById(idProduct);
        if (product == null) {
            System.out.println("El Producto no existe");
            return false;
        }
        if (product.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }
        product.setProductDeleted(true);
        productRepository.save(product);
        return true;
    }

    public boolean changeProductPrice(String idProduct, float newPrice) throws Exception {
        // Obtengo el Product de la BD
        Product product = productRepository.getProductById(idProduct);
        // Si el Product no existe, retorno false
        if (isNull(product)) {
            System.out.println("El Producto no existe");
            return false;
        }
        if (product.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        List<HistoricalProductPrice> historical = product.getPrices();

        int lastIndex = historical.size() - 1;
        HistoricalProductPrice last = historical.get(lastIndex);
        last.setFinishDate(LocalDate.now());
        last = historicalProductPriceRepository.save(last);
        historical.set(lastIndex, last);

        product.setPrice(newPrice);
        HistoricalProductPrice hp = new HistoricalProductPrice(product);
        hp = historicalProductPriceRepository.save(hp);

        product.addHistoricalPrice(hp);

        productRepository.save(product);

        return true;
    }

    public List<Product> getProductList() throws Exception {
        return productRepository.findAll();
    }

    public List<HistoricalProductPrice> getHistoricalPricesFromProduct(String idProduct) throws Exception {
        return historicalProductPriceRepository.findByProduct_Id(idProduct);
    }

    public List<ProductAndType> getProductsFromSupplier(String idSupplier) throws Exception {
        return productRepository.findBySupplierId(idSupplier);
    }

    public List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(LocalDate from, LocalDate to, String idProduct) throws Exception {
        List<HistoricalProductPrice> historico = historicalProductPriceRepository.findByProduct_IdAndStartDateGreaterThanAndFinishDateLessThan(idProduct, from, to);
        historico.add(historicalProductPriceRepository.findByProduct_IdAndStartDateGreaterThanAndFinishDateIsNull(idProduct, from));
        return historico;
    }

}
