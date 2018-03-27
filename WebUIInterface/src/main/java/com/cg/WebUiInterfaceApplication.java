package com.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = "com.cg")
public class WebUiInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebUiInterfaceApplication.class, args);
	}
}
