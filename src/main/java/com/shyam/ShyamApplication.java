package com.shyam;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShyamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShyamApplication.class, args);
	}
	@PostConstruct
	public void checkRedisConfig() {
		System.out.println("REDIS_HOST = " + System.getenv("REDIS_HOST"));
		System.out.println("REDIS_PORT = " + System.getenv("REDIS_PORT"));
		System.out.println("REDIS_PASSWORD = " + System.getenv("REDIS_PASSWORD"));
	}

}
