package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_TYPE_NOT_EXISTS = "The ProductType don't exists";
    private final HistoricalProductPriceRepository historicalProductPriceRepository;
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductServiceImpl(final HistoricalProductPriceRepository historicalProductPriceRepository,
                              final ProductRepository productRepository,
                              final ProductTypeRepository productTypeRepository,
                              final SupplierRepository supplierRepository) {
        this.historicalProductPriceRepository = historicalProductPriceRepository;
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.supplierRepository = supplierRepository;
    }

    public Product saveProduct(Product product) throws Exception {
        return productRepository.save(product);
    }

    public Product createNewProduct(Product product) throws Exception {
        if ((product.getName().isBlank()) ||
                (product.getDescription().isBlank()) ||
                (product.getPrice() < 0) ||
                (product.getWeight() < 0)) return null;

        // Corroboro que el Supplier haya sido proveido
        if (product.getSupplier() == null) {
            System.out.println("Supplier no provisto");
            return null;
        }

        // Corroboro que el ProductType haya sido proveido
        if (product.getType() == null) {
            System.out.println("ProductType no provisto");
            return null;
        }

        // Obtengo el Supplier de la BD
        Supplier supplier = supplierRepository
                .findById(product.getSupplier().getId())
                .orElseThrow();
        // Si el supplier no existe, retorno null
        if (supplier == null) {
            System.out.println("El Supplier no existe");
            return null;
        }

        // Obtengo el ProductType de la BD
        ProductType productType = productTypeRepository
                .findById(product.getType().getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, PRODUCT_TYPE_NOT_EXISTS));

        product.setSupplier(supplier);
        product.setType(productType);

        // Creo el historico del producto
        HistoricalProductPrice hp = new HistoricalProductPrice(product);
        product.addHistoricalPrice(hp);

        // Grabo el producto
        product = productRepository.save(product);

        // Actualizo  el supplier y el productType
        supplier.addProductt(product);
        productType.addProduct(product);
        supplierRepository.save(supplier);
        productTypeRepository.save(productType);

        return product;
    }

    public boolean updateProduct(Product product) throws Exception {
        if ((Long.valueOf(product.getId()) < 0) ||
                (product.getName().isBlank()) ||
                (product.getDescription().isBlank()) ||
                (product.getPrice() < 0) ||
                (product.getWeight() < 0)) return false;

        Product dbProducto = productRepository
                .findById(product.getId())
                .orElse(null);

        if (dbProducto == null) {
            System.out.println("El producto no existe");
            return false;
        }
        if (dbProducto.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        dbProducto.setName(product.getName());
        dbProducto.setPrice(product.getPrice());
        dbProducto.setDescription(product.getDescription());
        dbProducto.setWeight(product.getWeight());

        productRepository.save(dbProducto);

        return true;
    }

    public boolean deleteProduct(String idProduct) throws Exception {
        Product product = productRepository
                .findById(idProduct)
                .orElse(null);

        if (product == null) {
            System.out.println("El Producto no existe");
            return false;
        }
        if (product.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        productRepository.delete(product);

        return true;
    }

    public boolean changeProductPrice(String idProduct, float newPrice) throws Exception {
        // Obtengo el Product de la BD
        Product product = productRepository
                .findById(idProduct)
                .orElse(null);

        if (product == null) {
            System.out.println("El Producto no existe");
            return false;
        }
        if (product.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        List<HistoricalProductPrice> historical = product.getPrices();

        // Obtengo el ultimo precio y le configura la fecha de fin
        int lastIndex = historical.size() - 1;
        HistoricalProductPrice last = historical.get(lastIndex);
        last.setFinishDate(LocalDate.now());
        historical.set(lastIndex, last);

        // Configuro el nuevo precio
        product.setPrice(newPrice);
        HistoricalProductPrice hp = new HistoricalProductPrice(product);
        product.addHistoricalPrice(hp);

        // Actualizo el producto
        productRepository.save(product);

        return true;
    }

    public List<Product> getProductList() throws Exception {
        return productRepository.findAll();
    }

    public List<HistoricalProductPrice> getHistoricalPricesFromProduct(String idProduct) throws Exception {
        return historicalProductPriceRepository.getHistoricalPricesListOrderByStartDate(idProduct);
    }

    public List<Product> getProductsFromSupplier(String idSupplier) throws Exception {
        return null;
    }

    public List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        return null;
    }

}
