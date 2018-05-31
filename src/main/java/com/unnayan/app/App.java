package com.unnayan.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.unnayan")
@EnableJpaRepositories("com.unnayan")
@EntityScan("com.unnayan")
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
