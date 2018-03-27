package com.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Justin Mathew
 *
 * Created On 15-Mar-2018
 */
@SpringBootApplication
public class RabbitMqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqDemoApplication.class, args);
	}
}
