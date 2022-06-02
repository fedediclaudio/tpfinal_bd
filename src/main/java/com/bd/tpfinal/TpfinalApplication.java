package com.bd.tpfinal;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TpfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpfinalApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(UserRepository userRepository){
		return args -> {
			User user = new User();
			userRepository.save(user);
		};
	}

}
