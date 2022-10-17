package com.example.ddd_start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DddStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(DddStartApplication.class, args);
	}

}
