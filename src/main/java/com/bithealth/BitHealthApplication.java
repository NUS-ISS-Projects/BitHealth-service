package com.bithealth;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BitHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitHealthApplication.class, args);
	}
}
