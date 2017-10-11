package com.packt.example.clientimplicit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class ClientImplicitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientImplicitApplication.class, args);
	}

	@GetMapping("/")
	public String client() {
		return "client";
	}

	@GetMapping("/callback")
	public String callback() {
		return "callback_page";
	}
}
