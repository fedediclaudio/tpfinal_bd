package com.bd.tpfinal;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TpfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpfinalApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(UserRepository repository){
		return args -> {
			User user = new User();
			repository.insert(user);
		};
	}

}
