package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class TpfinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpfinalApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(ClientRepository clientRepository, DeliveryManRepository deliveryManRepository,
                             AddressRepository addressRepository, ProductTypeRepository productTypeRepository,
                             SupplierRepository supplierRepository, SupplierTypeRepository supplierTypeRepository) {
        return args -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            String dateInString = "08-Nov-1986";
            Date date = formatter.parse(dateInString);


            float f[];
            f = new float[2];
            f[0] = 10.10f;
            f[1] = 30.3f;

            Address address = new Address();
            address.setName("Siempre viva");
            address.setAddress("Calle falsa 123");
            address.setApartment("1");
            address.setCoords(f);
            address.setDescription("Una direccion linda");
            address.setOrders(new ArrayList<>());
            List<Address> addresses = new ArrayList<>();
            addresses.add(address);
            addressRepository.save(address);


            Client client = new Client();
            client.setName("German");
            client.setUsername("Gianotti");
            client.setPassword("GermanTheBest");
            client.setEmail("gggianotti@gmail.com");
            client.setDateOfBirth(date);
            client.setActive(Boolean.TRUE);
            client.setScore(50);

            client.setDateOfRegister(date);

            client.setAddresses(addresses);

            address.setClient(client);
            clientRepository.save(client);

            DeliveryMan deliveryMan = new DeliveryMan();
            deliveryMan.setName("German Delivery");
            deliveryMan.setUsername("Gianotti");
            deliveryMan.setPassword("GermanTheBest");
            deliveryMan.setEmail("gggianotti@gmail.com");
            deliveryMan.setDateOfBirth(date);
            deliveryMan.setActive(Boolean.TRUE);
            deliveryMan.setScore(50);

            deliveryMan.setNumberOfSuccessOrders(5);
            deliveryMan.setFree(Boolean.FALSE);
            deliveryMan.setDateOfAdmission(date);
            deliveryMan.setOrdersPending(new ArrayList<>());

            deliveryManRepository.save(deliveryMan);


            Qualification qualification = new Qualification();
            qualification.setScore(5f);
            qualification.setCommentary("Some Comment");


            /*
             * Product Type
             */
            ProductType productType = new ProductType();
            productType.setName("Drink");
            productType.setDescription("Some Description");
            productTypeRepository.save(productType);

            /*
             *Historical Product Price
             */
            String stringDateStart = "01-Jun-2022";
            String stringDateEnd = "30-Jun-2022";
            Date dateStart = formatter.parse(stringDateStart);
            Date dateEnd = formatter.parse(stringDateEnd);
            HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice();
            historicalProductPrice.setPrice(10f);
            historicalProductPrice.setStartDate(dateStart);
            historicalProductPrice.setFinishDate(dateEnd);


            /*
            Supplier Type
             */
            SupplierType supplierType = new SupplierType();
            supplierType.setName("Name");
            supplierType.setDescription("Some description");
            supplierTypeRepository.save(supplierType);

            /*
            Supplier
             */
            float coords[];
            coords = new float[2];
            coords[0] = 10.10f;
            coords[1] = 30.3f;
            Supplier supplier = new Supplier();
            supplier.setName("Supplier Name");
            supplier.setType(supplierType);
            supplier.setQualificationOfUsers(new Float(5f));
            supplier.setAddress("Some Address");
            supplier.setCoords(coords);
            supplier.setCuil("Some Cuil");

            supplierRepository.save(supplier);

            /*
            Product
             */


        };
    }

}
