package com.cg.rabbitmq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cg.rabbitmq"})
public class MessageContentCheckingWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageContentCheckingWebApplication.class, args);
	}
}
