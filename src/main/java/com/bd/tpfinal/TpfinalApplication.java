package com.bd.tpfinal;

import com.bd.tpfinal.utils.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TpfinalApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(TpfinalApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationStartRunner()
	{
		return new ApplicationRunner();
	}
}
