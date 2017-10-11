package com.packt.example.customclaimsjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity
public class CustomClaimsJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomClaimsJwtApplication.class, args);
	}
}
