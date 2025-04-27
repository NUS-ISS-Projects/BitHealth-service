package com.bithealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.bithealth")
@EnableJpaRepositories(basePackages = "com.bithealth.repositories")
@EntityScan(basePackages = "com.bithealth.entities")
@EnableJpaAuditing
public class BitHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitHealthApplication.class, args);
	}
}
