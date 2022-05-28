package com.bd.tpfinal;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.repositories.ClientRepository;
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
    CommandLineRunner runner(ClientRepository clientRepository) {
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
            client.setId("0fe4cb40-de2e-11ec-9d64-0242ac120002");
            client.setUsername("Gianotti");
            client.setPassword("GermanElMasMejor");
            client.setEmail("gggianotti@gmail.com");
            client.setDateOfBirth(date);
            client.setScrore(Boolean.TRUE);
            client.setScore(50);

            client.setDateOfRegister(date);
            client.setAddresses(Arrays.asList(address));

            clientRepository.save(client);
        };
    }

}
