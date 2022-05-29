package com.bd.tpfinal;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
public class TpfinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpfinalApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(ClientRepository clientRepository, DeliveryManRepository deliveryManRepository) {
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
            address.setAddress("Calle falsa");
            address.setApartment("1");
            address.setCoords(f);
            address.setDescription("Una direccion linda");
            address.setClient(new Client());
            address.setOrders(new ArrayList<>());


            Client client = new Client();
            client.setName("German");
            client.setUsername("Gianotti");
            client.setPassword("GermanElMasMejor");
            client.setEmail("gggianotti@gmail.com");
            client.setDateOfBirth(date);
            client.setActive(Boolean.TRUE);
            client.setScore(50);

            client.setDateOfRegister(date);
            client.setAddresses(Arrays.asList(address));

            clientRepository.save(client);

            DeliveryMan deliveryMan = new DeliveryMan();
            deliveryMan.setName("German Delivery");
            deliveryMan.setUsername("Gianotti");
            deliveryMan.setPassword("GermanElMasMejor");
            deliveryMan.setEmail("gggianotti@gmail.com");
            deliveryMan.setDateOfBirth(date);
            deliveryMan.setActive(Boolean.TRUE);
            deliveryMan.setScore(50);

            deliveryMan.setNumberOfSuccessOrders(5);
            deliveryMan.setFree(Boolean.FALSE);
            deliveryMan.setDateOfAdmission(date);
            deliveryMan.setOrdersPending(new ArrayList<>());

            deliveryManRepository.save(deliveryMan);
        };
    }

}
