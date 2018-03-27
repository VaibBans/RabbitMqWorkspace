package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.example"})
@SpringBootApplication
public class WebMessagingDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebMessagingDatabaseApplication.class, args);
	}
}