package com.bd.tpfinal.seeders;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DatabaseSeeder {
    private final Logger logger = Logger.getLogger(String.valueOf(DatabaseSeeder.class));
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;
    @Autowired
    private SupplierTypeRepository supplierTypeRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DeliveryManRepository deliveryManRepository;
    @Autowired
    private AddressRepository addressRepository;

    @EventListener
    public void seedTables(ContextRefreshedEvent event) throws Exception {
     /*   seedProductTypeTable();
        seedSupplierTypeTable();
        seedSupplierTable();
        seedPersonsTable();
        seedOrderTable();*/
    }

    private void seedProductTypeTable() {
        ProductType productType_1 = new ProductType();
        productType_1.setName("Almacen");
        productType_1.setDescription("Productos almacen");

        ProductType productType_2 = new ProductType();
        productType_2.setName("Limpieza");
        productType_2.setDescription("Productos limpieza");

        ProductType productType_3 = new ProductType();
        productType_3.setName("Verduleria");
        productType_3.setDescription("Productos verduleria");

        ProductType productType_5 = new ProductType();
        productType_5.setName("Panificados");
        productType_5.setDescription("Productos de panadería");

        ProductType productType_6 = new ProductType();
        productType_6.setName("Café");
        productType_6.setDescription("Productos de café");

        //---------------
        ProductType productType_7 = new ProductType();
        productType_7.setName("Muebles de interior");
        productType_7.setDescription("Productos de mueblería");

        ProductType productType_15 = new ProductType();
        productType_15.setName("Muebles de exterior");
        productType_15.setDescription("Productos de mueblería");

        //-------------------------------
        ProductType productType_8 = new ProductType();
        productType_8.setName("Aire libre y jardín");
        productType_8.setDescription("Productos para Aire libre y jardín");

        ProductType productType_10 = new ProductType();
        productType_10.setName("Tiempo libre, deporte y entretenimiento");
        productType_10.setDescription("Productos para tiempo libre, deporte y entretenimiento");


        ProductType productType_4 = new ProductType();
        productType_4.setName("Autopartes");
        productType_4.setDescription("Productos autopartes");


        //--------------------------
        ProductType productType_9 = new ProductType();
        productType_9.setName("Herramientas");
        productType_9.setDescription("Productos para herramientas");

        ProductType productType_11 = new ProductType();
        productType_11.setName("Construccion");
        productType_11.setDescription("Productos para construccion");

        //----------------------------
        ProductType productType_12 = new ProductType();
        productType_12.setName("Indumentaria y moda");
        productType_12.setDescription("Productos para indumentaria y moda");

        //----------------
        ProductType productType_13 = new ProductType();
        productType_13.setName("Alimento para animales");
        productType_13.setDescription("Productos para mascotas");

        ProductType productType_14 = new ProductType();
        productType_14.setName("Casas para animales");
        productType_14.setDescription("Productos para mascotas");

        productTypeRepository.saveAll(Arrays.asList(productType_1, productType_2, productType_3,
                productType_4, productType_5, productType_6, productType_7, productType_8, productType_9,
                productType_10, productType_11, productType_12, productType_13, productType_14, productType_15));
        logger.info("ProductType table Cargada");

    }

    private void seedSupplierTypeTable() {

        SupplierType supplierType_1 = new SupplierType();
        supplierType_1.setDescription("Productos variados, almacen limpieza verduleria ");
        supplierType_1.setName("Supermercado");

        SupplierType supplierType_2 = new SupplierType();
        supplierType_2.setDescription("Productos muebleria");
        supplierType_2.setName("Muebleria");

        SupplierType supplierType_3 = new SupplierType();
        supplierType_3.setDescription("Productos de jardineria");
        supplierType_3.setName("Artículos de exterior");

        SupplierType supplierType_4 = new SupplierType();
        supplierType_4.setDescription("Productos de mascotas");
        supplierType_4.setName("Artículos para mascotas");

        SupplierType supplierType_5 = new SupplierType();
        supplierType_5.setDescription("Productos variados mayorista");
        supplierType_5.setName("Artículos mayoristas");

        SupplierType supplierType_6 = new SupplierType();
        supplierType_6.setDescription("Productos indumentaria moda calzado accesorios");
        supplierType_6.setName("Indumentaria");

        SupplierType supplierType_7 = new SupplierType();
        supplierType_7.setDescription("Productos panificados");
        supplierType_7.setName("Panaderia");

        supplierTypeRepository.saveAll(Arrays.asList(supplierType_1, supplierType_2,
                supplierType_3, supplierType_4, supplierType_5, supplierType_6, supplierType_7));
    }

    private void seedSupplierTable() {
        List<Product> generalProducts;
        List<Product> generalProductsAireLibre;
        List<Product> generalProductsMuebles;
        List<Product> generalProductsIndumentaria;
        List<Product> generalProductsPan;

        Supplier supplier_1 = new Supplier();
        supplier_1.setAddress("Soberania Nacional 200");
        supplier_1.setName("Carrefour");
        supplier_1.setCoords(new float[]{-43.44f, 10.22f});
        supplier_1.setCuil("7022221224");
        supplier_1.setQualificationOfUsers(4.3f);
        generalProducts = createAndReturnAlmancenLimpiezaVerduleriaProducts(supplier_1, 32);
        generalProductsAireLibre = createAndReturnAireLibreProducts(supplier_1, 32);
        generalProductsPan = createAndReturnPanProducts(supplier_1, 32);

        List<Product> listaFinal = Stream.of(generalProducts, generalProductsAireLibre, generalProductsPan)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        supplier_1.setProducts(generalProducts);

        supplier_1.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("Supermercado").get(0));

        supplierRepository.save(supplier_1);

        generalProducts.clear();
        generalProductsAireLibre.clear();
        generalProductsPan.clear();
        listaFinal.clear();

        Supplier supplier_2 = new Supplier();
        supplier_2.setAddress("Ruta 3");
        supplier_2.setName("Diarco");
        supplier_2.setCoords(new float[]{-143.44f, 310.22f});
        supplier_2.setCuil("4343432322");
        supplier_2.setQualificationOfUsers(2.3f);
        generalProducts = createAndReturnAlmancenLimpiezaVerduleriaProducts(supplier_2, 231);
        supplier_2.setProducts(generalProducts);
        supplier_2.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("mayorista").get(0));
        supplierRepository.save(supplier_2);
        generalProducts.clear();

        Supplier supplier_3 = new Supplier();
        supplier_3.setAddress("Av Irigoyen");
        supplier_3.setName("La Anónima");
        supplier_3.setCoords(new float[]{-543.44f, 610.22f});
        supplier_3.setCuil("321213321");
        supplier_3.setQualificationOfUsers(4.6f);
        generalProducts = createAndReturnAlmancenLimpiezaVerduleriaProducts(supplier_3, 80);
        generalProductsIndumentaria = createAndReturnIndumentariaProducts(supplier_3, 80);


        listaFinal = Stream.of(generalProducts, generalProductsIndumentaria)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        supplier_3.setProducts(listaFinal);

        supplier_3.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("supermercado").get(0));
        supplierRepository.save(supplier_3);
        generalProducts.clear();
        generalProductsIndumentaria.clear();
        listaFinal.clear();

        Supplier supplier_4 = new Supplier();
        supplier_4.setAddress("Calle 22");
        supplier_4.setName("La Casita");
        supplier_4.setCoords(new float[]{-546.44f, 10.22f});
        supplier_4.setCuil("21211121");
        supplier_4.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnMueblesProducts(supplier_4, 4);

        supplier_4.setProducts(generalProducts);

        supplier_4.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("mueble").get(0));
        supplierRepository.save(supplier_4);
        generalProducts.clear();
        listaFinal.clear();

        Supplier supplier_5 = new Supplier();
        supplier_5.setAddress("Calle los abedules");
        supplier_5.setName("Easy");
        supplier_5.setCoords(new float[]{-546.44f, 10.22f});
        supplier_5.setCuil("23214421");
        supplier_5.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnToolsConstructionProducts(supplier_5, 5);
        generalProductsAireLibre = createAndReturnAireLibreProducts(supplier_5, 5);
        generalProductsMuebles = createAndReturnMueblesProducts(supplier_5, 5);

        listaFinal = Stream.of(generalProducts, generalProductsAireLibre, generalProductsMuebles)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        supplier_5.setProducts(listaFinal);

        supplier_5.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("mayorista").get(0));
        supplierRepository.save(supplier_5);
        generalProducts.clear();
        generalProductsAireLibre.clear();
        generalProductsMuebles.clear();
        listaFinal.clear();

        Supplier supplier_6 = new Supplier();
        supplier_6.setAddress("Fontana 25");
        supplier_6.setName("Braian");
        supplier_6.setCoords(new float[]{-546.44f, 10.22f});
        supplier_6.setCuil("23014420");
        supplier_6.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnIndumentariaProducts(supplier_6, 6);
        supplier_6.setProducts(generalProducts);
        supplier_6.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("Indumentaria").get(0));
        supplierRepository.save(supplier_6);
        generalProducts.clear();

        Supplier supplier_7 = new Supplier();
        supplier_7.setAddress("Mitre 1254");
        supplier_7.setName("Todo para tus mascotas");
        supplier_7.setCoords(new float[]{-546.44f, 10.22f});
        supplier_7.setCuil("23698420");
        supplier_7.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnMascotasProducts(supplier_7, 7);
        supplier_7.setProducts(generalProducts);
        supplier_7.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("mascotas").get(0));
        supplierRepository.save(supplier_7);
        generalProducts.clear();

        Supplier supplier_8 = new Supplier();
        supplier_8.setAddress("Soberania nacional 9857");
        supplier_8.setName("las 5 estaciones");
        supplier_8.setCoords(new float[]{-546.44f, 10.22f});
        supplier_8.setCuil("23698378");
        supplier_8.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnAireLibreProducts(supplier_8, 8);
        supplier_8.setProducts(generalProducts);
        supplier_8.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("exterior").get(0));
        supplierRepository.save(supplier_8);
        generalProducts.clear();


        Supplier supplier_9 = new Supplier();
        supplier_9.setAddress("Soberania nacional 9857");
        supplier_9.setName("El rey de la medialuna");
        supplier_9.setCoords(new float[]{-546.44f, 10.22f});
        supplier_9.setCuil("23608378");
        supplier_9.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnPanProducts(supplier_9, 9);
        supplier_9.setProducts(generalProducts);
        supplier_9.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("panaderia").get(0));
        supplierRepository.save(supplier_9);
        generalProducts.clear();


        Supplier supplier_10 = new Supplier();
        supplier_10.setAddress("soler 100");
        supplier_10.setName("La astilla");
        supplier_10.setCoords(new float[]{-546.44f, 10.22f});
        supplier_10.setCuil("20608378");
        supplier_10.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnMueblesProducts(supplier_10, 10);
        supplier_10.setProducts(generalProducts);
        supplier_10.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("Muebleria").get(0));
        supplierRepository.save(supplier_10);
        generalProducts.clear();


        Supplier supplier_11 = new Supplier();
        supplier_11.setAddress("cuba 652");
        supplier_11.setName("Los 5 palos");
        supplier_11.setCoords(new float[]{-546.44f, 10.22f});
        supplier_11.setCuil("23608378");
        supplier_11.setQualificationOfUsers(4.5f);
        generalProducts = createAndReturnMueblesProducts(supplier_11, 11);
        generalProductsAireLibre = createAndReturnAireLibreProducts(supplier_11, 11);
        listaFinal = Stream.of(generalProducts, generalProductsAireLibre)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        supplier_11.setProducts(listaFinal);

        supplier_11.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("Muebleria").get(0));
        supplierRepository.save(supplier_11);
        generalProducts.clear();
        generalProductsAireLibre.clear();
        listaFinal.clear();

        logger.info("Supplier table Cargada");
    }


    private List<Product> createAndReturnAlmancenLimpiezaVerduleriaProducts(Supplier aSupplier, int i) {
        List<Product> products = new ArrayList<>();
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_3 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_4 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_5 = Math.round(i + r.nextFloat() * (20 - 10));


        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Almacen");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Limpieza");
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Panificados");
        Optional<ProductType> type_4 = productTypeRepository.findProductTypesByNameIgnoreCase("Verduleria");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Arroz en caja Luchetti");
        prod_1.setWeight(5.0f);
        prod_1.setName("Arroz");
        prod_1.setSupplier(aSupplier);
        if (type_1.isPresent())
            prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Odex");
        prod_2.setWeight(3.0f);
        prod_2.setName("Desinfectante de cocina");
        prod_2.setSupplier(aSupplier);
        if (type_2.isPresent())
            prod_2.setType(Arrays.asList(type_2.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Cerveza artesanal Patagonia");
        prod_3.setWeight(70.0f);
        prod_3.setName("Cerveza");
        prod_3.setSupplier(aSupplier);
        if (type_3.isPresent())
            prod_3.setType(Arrays.asList(type_1.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Skip para lavarropas");
        prod_4.setWeight(3.0f);
        prod_4.setName("Jabon liquido");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_2.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("Repollada Bolivia");
        prod_5.setWeight(70.0f);
        prod_5.setName("Lechuga");
        prod_5.setSupplier(aSupplier);
        prod_5.setType(Arrays.asList(type_1.get(), type_4.get()));
        products.add(prod_5);

        Product prod_6 = new Product();
        prod_6.setPrice(random_5);
        prod_6.setDescription("Moño azul");
        prod_6.setWeight(70.0f);
        prod_6.setName("Manzanas");
        prod_6.setSupplier(aSupplier);
        prod_6.setType(Arrays.asList(type_1.get(), type_4.get()));
        products.add(prod_6);

        Product prod_7 = new Product();
        prod_7.setPrice(random_1);
        prod_7.setDescription("en grano Juan Valdez");
        prod_7.setWeight(30.0f);
        prod_7.setName("Café");
        prod_7.setSupplier(aSupplier);
        prod_7.setType(Arrays.asList(type_1.get()));
        products.add(prod_7);

        Product prod_8 = new Product();
        prod_8.setPrice(random_1);
        prod_8.setDescription("Luchetti moñito");
        prod_8.setWeight(5.0f);
        prod_8.setName("Fideos");
        prod_8.setSupplier(aSupplier);
        prod_8.setType(Arrays.asList(type_1.get()));
        products.add(prod_8);

        Product prod_9 = new Product();
        prod_9.setPrice(random_1);
        prod_9.setDescription("milonguita");
        prod_9.setWeight(5.0f);
        prod_9.setName("Pan");
        prod_9.setSupplier(aSupplier);
        prod_9.setType(Arrays.asList(type_3.get()));
        products.add(prod_9);

        productRepository.saveAll(Arrays.asList(prod_1, prod_2, prod_3, prod_4, prod_5, prod_6, prod_7, prod_8, prod_9));
        return products;
    }

    private List<Product> createAndReturnIndumentariaProducts(Supplier aSupplier, int i) {
        List<Product> products = new ArrayList<>();
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (20 - 10));

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Indumentaria");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Levis tiro alto");
        prod_1.setWeight(5.0f);
        prod_1.setName("Pantalon");
        prod_1.setSupplier(aSupplier);

        if (type_1.isPresent())
            prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("DC manga larga");
        prod_2.setWeight(3.0f);
        prod_2.setName("Remera");
        prod_2.setSupplier(aSupplier);
        if (type_1.isPresent())
            prod_2.setType(Arrays.asList(type_1.get()));

        products.add(prod_2);

        productRepository.saveAll(Arrays.asList(prod_1, prod_2));
        return products;
    }

    private List<Product> createAndReturnMascotasProducts(Supplier aSupplier, int i) {
        List<Product> products = new ArrayList<>();
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_3 = Math.round(i + r.nextFloat() * (20 - 10));
        float random_4 = Math.round(i + r.nextFloat() * (20 - 10));

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Alimento para animales");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Casas para animales");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Proplan bebe");
        prod_1.setWeight(5.0f);
        prod_1.setName("Alimento para perro");
        prod_1.setSupplier(aSupplier);
        if (type_1.isPresent())
            prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Proplan adulto");
        prod_2.setWeight(3.0f);
        prod_2.setName("Alimento para gato");
        prod_2.setSupplier(aSupplier);
        prod_2.setType(Arrays.asList(type_1.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Plastica grande");
        prod_3.setWeight(70.0f);
        prod_3.setName("Cucha para perro");
        prod_3.setSupplier(aSupplier);
        prod_3.setType(Arrays.asList(type_1.get(), type_2.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Curavich");
        prod_4.setWeight(3.0f);
        prod_4.setName("Pipeta para perro");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_1.get()));
        products.add(prod_4);
        productRepository.saveAll(Arrays.asList(prod_1, prod_2, prod_3, prod_4));

        return products;
    }

    private List<Product> createAndReturnToolsConstructionProducts(Supplier aSupplier, int i) {
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_4 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_3 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_5 = Math.round(i + r.nextFloat() * (100 - 10));
        List<Product> products = new ArrayList<>();

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Construccion");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Herramientas");
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Autopartes");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Hueco");
        prod_1.setWeight(30.0f);
        prod_1.setName("Ladrillo");
        prod_1.setSupplier(aSupplier);
        prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("macizo");
        prod_2.setWeight(35.0f);
        prod_2.setName("Ladrillo");
        prod_2.setSupplier(aSupplier);
        prod_2.setType(Arrays.asList(type_1.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("electrica");
        prod_3.setWeight(30.0f);
        prod_3.setName("Hormigonera");
        prod_3.setSupplier(aSupplier);
        prod_3.setType(Arrays.asList(type_1.get(), type_2.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("percutor electrico");
        prod_4.setWeight(21.0f);
        prod_4.setName("Taladro");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_2.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("perfil bajo");
        prod_5.setWeight(15.0f);
        prod_5.setName("Cubiertas");
        prod_5.setSupplier(aSupplier);
        prod_5.setType(Arrays.asList(type_3.get()));
        products.add(prod_5);
        productRepository.saveAll(Arrays.asList(prod_1, prod_2, prod_3, prod_4, prod_5));

        return products;
    }

    private List<Product> createAndReturnPanProducts(Supplier aSupplier, int i) {
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_3 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_4 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_5 = Math.round(i + r.nextFloat() * (100 - 10));
        List<Product> products = new ArrayList<>();

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Panificados");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Milonguita");
        prod_1.setWeight(30.0f);
        prod_1.setName("Pan");
        prod_1.setSupplier(aSupplier);
        prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("surtida");
        prod_2.setWeight(35.0f);
        prod_2.setName("Facturas");
        prod_2.setSupplier(aSupplier);
        prod_2.setType(Arrays.asList(type_1.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("de miga");
        prod_3.setWeight(30.0f);
        prod_3.setName("sanguches");
        prod_3.setSupplier(aSupplier);
        prod_3.setType(Arrays.asList(type_1.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("variadas");
        prod_4.setWeight(21.0f);
        prod_4.setName("tartas");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_1.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("a pedido");
        prod_5.setWeight(15.0f);
        prod_5.setName("tortas");
        prod_5.setSupplier(aSupplier);
        prod_5.setType(Arrays.asList(type_1.get()));
        products.add(prod_5);
        productRepository.saveAll(Arrays.asList(prod_1, prod_2, prod_3, prod_4, prod_5));
        return products;
    }

    private List<Product> createAndReturnAireLibreProducts(Supplier aSupplier, int i) {
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_3 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_4 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_5 = Math.round(i + r.nextFloat() * (100 - 10));
        List<Product> products = new ArrayList<>();

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Aire libre y jardín");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Tiempo libre, deporte y entretenimiento");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("de 2 por 2");
        prod_1.setWeight(30.0f);
        prod_1.setName("mesa de plastico");
        prod_1.setSupplier(aSupplier);
        prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("de 5 metros de diametro");
        prod_2.setWeight(35.0f);
        prod_2.setName("cama elastica");
        prod_2.setSupplier(aSupplier);
        prod_2.setType(Arrays.asList(type_1.get(), type_2.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("de tela de 4 metros de diametro");
        prod_3.setWeight(30.0f);
        prod_3.setName("sombrilla");
        prod_3.setSupplier(aSupplier);
        prod_3.setType(Arrays.asList(type_1.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("de plastico por unidad");
        prod_4.setWeight(21.0f);
        prod_4.setName("banqueta");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_1.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("Pelopincho redonda de 5 metros");
        prod_5.setWeight(15.0f);
        prod_5.setName("pileta de lona");
        prod_5.setSupplier(aSupplier);
        prod_5.setType(Arrays.asList(type_1.get(), type_2.get()));

        HistoricalProductPrice historicalProductPrice_1 = new HistoricalProductPrice();
        historicalProductPrice_1.setProduct(prod_5);
        historicalProductPrice_1.setStartDate((new GregorianCalendar(2020, 1, 8).getTime()));
        historicalProductPrice_1.setFinishDate(new Date());
        historicalProductPriceRepository.save(historicalProductPrice_1);
        prod_5.setPrices(Arrays.asList(historicalProductPrice_1));
        products.add(prod_5);

        productRepository.saveAll(Arrays.asList(prod_1, prod_2, prod_3, prod_4, prod_5));

        return products;
    }

    private List<Product> createAndReturnMueblesProducts(Supplier aSupplier, int i) {
        Random r = new Random();
        float random_1 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_2 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_3 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_4 = Math.round(i + r.nextFloat() * (100 - 10));
        float random_5 = Math.round(i + r.nextFloat() * (100 - 10));
        List<Product> products = new ArrayList<>();

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Muebles de interior");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Muebles de exterior");
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Aire libre y jardín");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("rectangular doble vidrio");
        prod_1.setWeight(30.0f);
        prod_1.setName("mesa de vidrio");
        prod_1.setSupplier(aSupplier);
        prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("de caño con pana");
        prod_2.setWeight(35.0f);
        prod_2.setName("silla");
        prod_2.setSupplier(aSupplier);
        prod_2.setType(Arrays.asList(type_1.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("4 sillas");
        prod_3.setWeight(30.0f);
        prod_3.setName("juego de mesa y silla de jardin");
        prod_3.setSupplier(aSupplier);
        prod_3.setType(Arrays.asList(type_2.get(), type_3.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("plegable");
        prod_4.setWeight(21.0f);
        prod_4.setName("reposera de lona");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_2.get(), type_3.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("redonda de 4 metros");
        prod_5.setWeight(15.0f);
        prod_5.setName("sombrilla");
        prod_5.setSupplier(aSupplier);
        prod_5.setType(Arrays.asList(type_3.get()));
        products.add(prod_5);
        productRepository.saveAll(Arrays.asList(prod_1, prod_2, prod_3, prod_4, prod_5));

        /**/
        return products;
    }

    private List<Product> createAndReturnGeneralProducts(Supplier aSupplier, int i) {
        List<Product> products = new ArrayList<>();
        Random r = new Random();
        float random_1 = i + r.nextFloat() * (20 - 10);
        float random_2 = i + r.nextFloat() * (20 - 10);
        float random_3 = i + r.nextFloat() * (20 - 10);
        float random_4 = i + r.nextFloat() * (20 - 10);
        float random_5 = i + r.nextFloat() * (20 - 10);
        float random_6 = i + r.nextFloat() * (20 - 10);
        float random_7 = i + r.nextFloat() * (20 - 10);

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Arroz en caja Luchetti");
        prod_1.setWeight(5.0f);
        prod_1.setName("Arroz");
        prod_1.setSupplier(aSupplier);
        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Almacen");
        if (type_1.isPresent())
            prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Desinfectante de cocina");
        prod_2.setWeight(3.0f);
        prod_2.setName("Odex");
        prod_2.setSupplier(aSupplier);
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Limpieza");
        if (type_2.isPresent())
            prod_2.setType(Arrays.asList(type_2.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Cerveza artesanal");
        prod_3.setWeight(70.0f);
        prod_3.setName("Cerveza Patagonia");
        prod_3.setSupplier(aSupplier);
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Comestibles");
        if (type_3.isPresent())
            prod_3.setType(Arrays.asList(type_3.get(), type_1.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Jabon liquido para lavarropas");
        prod_4.setWeight(3.0f);
        prod_4.setName("Skip");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_2.get()));
        products.add(prod_4);

        return products;
    }

    private List<Product> createAndReturnCoffeeProducts(Supplier aSupplier, int i) {
        Random r = new Random();
        float random_1 = i + r.nextFloat() * (100 - 10);
        float random_2 = i + r.nextFloat() * (100 - 10);
        float random_3 = i + r.nextFloat() * (100 - 10);
        float random_4 = i + r.nextFloat() * (100 - 10);
        float random_5 = i + r.nextFloat() * (100 - 10);
        List<Product> products = new ArrayList<>();

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Almacen");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Comestibles");
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Café");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Café en grano");
        prod_1.setWeight(30.0f);
        prod_1.setName("Juan Valdez");
        prod_1.setSupplier(aSupplier);
        if (type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_1.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Café instantaneo");
        prod_2.setWeight(35.0f);
        prod_2.setName("Nescafe");
        prod_2.setSupplier(aSupplier);
        if (type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_2.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Cafe molido");
        prod_3.setWeight(30.0f);
        prod_3.setName("Starbucks Pike Place");
        prod_3.setSupplier(aSupplier);
        if (type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_3.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Cafe en cápsulas");
        prod_4.setWeight(21.0f);
        prod_4.setName("Nespresso");
        prod_4.setSupplier(aSupplier);
        if (type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_4.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("Cafe en saquitos");
        prod_5.setWeight(15.0f);
        prod_5.setName("Bonafide");
        prod_5.setSupplier(aSupplier);
        if (type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_5.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_5);
        return products;
    }

    public void seedPersonsTable() {

        Client client_1 = new Client();
        client_1.setName("Juan Perez");
        client_1.setDateOfRegister(new GregorianCalendar(2019, 2, 11).getTime());
        client_1.setEmail("john@doe.com");
        client_1.setScore(10);
        client_1.setDateOfBirth(new GregorianCalendar(1900, 1, 8).getTime());
        client_1.setUsername("johnnydoe");
        client_1.setPassword("johndoe01");

        Address address_1 = new Address();
        address_1.setDescription("Domicilio particular");
        address_1.setName("Calle Muzio");
        address_1.setCoords(new float[]{-103.44f, 102.22f});
        address_1.setApartment("C");
        address_1.setAddress("Muzio 26");
        address_1.setClient(client_1);

        addressRepository.save(address_1);

        Address address_2 = new Address();
        address_2.setDescription("Oficina");
        address_2.setName("Empresa");
        address_2.setCoords(new float[]{-122.44f, 12.22f});
        address_2.setApartment("Ninguno");
        address_2.setAddress("Belgrano 521");
        address_2.setClient(client_1);

        addressRepository.save(address_2);

        client_1.setAddresses(Arrays.asList(address_1, address_2));
        clientRepository.save(client_1);
        Client client_2 = new Client();
        client_2.setName("Luisa Soto");
        client_2.setDateOfRegister(new GregorianCalendar(2011, 4, 21).getTime());
        client_2.setEmail("luisa@pedidosya.com");
        client_2.setScore(8);
        client_2.setDateOfBirth(new GregorianCalendar(1989, 1, 8).getTime());
        client_2.setUsername("luisa");
        client_2.setPassword("luisa");

        Address address_3 = new Address();
        address_3.setDescription("Empresa");
        address_3.setName("Edificio");
        address_3.setCoords(new float[]{-123.44f, 112.22f});
        address_3.setApartment("D");
        address_3.setAddress("Av. Fontana 231");
        address_3.setClient(client_2);
        addressRepository.save(address_3);
        client_2.setAddresses(Arrays.asList(address_3));
        clientRepository.save(client_2);

        Client client_3 = new Client();
        client_3.setName("Sara Gomez");
        client_3.setDateOfRegister(new GregorianCalendar(1985, 4, 21).getTime());
        client_3.setEmail("sgomez@bttf.com");
        client_3.setScore(5);
        client_3.setDateOfBirth(new GregorianCalendar(2014, 3, 5).getTime());
        client_3.setUsername("sgomez");
        client_3.setPassword("sgomez");

        Address address_4 = new Address();
        address_4.setDescription("Departamento a la calle");
        address_4.setName("Depto");
        address_4.setCoords(new float[]{-132.54f, 142.22f});
        address_4.setApartment("A");
        address_4.setAddress("Marconi 531");
        address_4.setClient(client_3);
        addressRepository.save(address_4);
        client_3.setAddresses(Arrays.asList(address_4));
        clientRepository.save(client_3);

        DeliveryMan deliveryMan_1 = new DeliveryMan();
        deliveryMan_1.setFree(true);
        deliveryMan_1.setName("Repartidor 1");
        deliveryMan_1.setNumberOfSuccessOrders(2);
        deliveryMan_1.setEmail("pizza@guy1.com");
        deliveryMan_1.setUsername("rep1");
        deliveryMan_1.setPassword("rep1");
        deliveryMan_1.setActive(true);
        deliveryMan_1.setScore(0);
        deliveryMan_1.setDateOfBirth(new GregorianCalendar(2014, 3, 5).getTime());
        deliveryMan_1.setOrdersPending(null);
        deliveryManRepository.save(deliveryMan_1);

        DeliveryMan deliveryMan_2 = new DeliveryMan();
        deliveryMan_2.setFree(true);
        deliveryMan_2.setName("Repartidor 2");
        deliveryMan_2.setNumberOfSuccessOrders(2);
        deliveryMan_2.setEmail("pizza@guy2.com");
        deliveryMan_2.setUsername("rep2");
        deliveryMan_2.setPassword("rep2");
        deliveryMan_2.setActive(true);
        deliveryMan_2.setScore(0);
        deliveryMan_2.setDateOfBirth(new GregorianCalendar(2018, 3, 5).getTime());
        deliveryMan_2.setOrdersPending(null);
        deliveryManRepository.save(deliveryMan_2);


        DeliveryMan deliveryMan_3 = new DeliveryMan();
        deliveryMan_3.setFree(true);
        deliveryMan_3.setName("Repartidor 3");
        deliveryMan_3.setNumberOfSuccessOrders(2);
        deliveryMan_3.setEmail("pizza@guy2.com");
        deliveryMan_3.setUsername("rep3");
        deliveryMan_3.setPassword("rep3");
        deliveryMan_3.setActive(true);
        deliveryMan_3.setScore(0);
        deliveryMan_3.setDateOfBirth(new GregorianCalendar(2018, 3, 5).getTime());
        deliveryMan_3.setOrdersPending(null);
        deliveryManRepository.save(deliveryMan_3);

        logger.info("Client table Cargada");
    }

    private void seedOrderTable() throws Exception {
        // Supplier CUIL y Client Username se usan debido a que los ObjectId son distintos en cada ejecución
        seedOrderTableSupplier("7022221224", "mesa", "luisa", "casa de rejas negras");
//        seedOrderTableSupplier(10L, "", 2L, "pasillo al fondo");
//        seedOrderTableSupplier(1L, "", 3L, "tocar el timbre");
//        seedOrderTableSupplier(8L, "pileta", 3L, "nada");
//        seedOrderTableSupplier(7L, "alimento", 2L, "sin aviso");
//        seedOrderTableSupplier(9L, "pan", 1L, "retiro");
//        seedOrderTableSupplier(1L, "fideo", 3L, "retiro en sucursal");
//        seedOrderTableSupplier(5L, "", 2L, "no funcionan las cuotas");
//        seedOrderTableSupplier(6L, "remera", 2L, "no aplica descuento por pago efectivo");
//        seedOrderTableSupplier(2L, "arroz", 1L, "contraentrega");
    }

    // Hay que rehacer la parte de orders.
    private void seedOrderTableSupplier(String supplier_cuil, String productName, String username, String comments) throws Exception {

        List<Product> productList = new ArrayList<Product>();
        List<Item> itemsProducts = new ArrayList<>();

        if (productName.isEmpty()) {
            Optional<Supplier> products_supplier_Id = supplierRepository.findSupplierByCuil("7022221224");
            products_supplier_Id.get().getProducts().forEach(productList::add);
        }

        Order order_1 = new Order();
        order_1.setComments(comments);
        order_1.setNumber(1);
        order_1.setDateOfOrder(new Date());
        order_1.setClient(new Client());

        orderRepository.save(order_1);
    }
}
