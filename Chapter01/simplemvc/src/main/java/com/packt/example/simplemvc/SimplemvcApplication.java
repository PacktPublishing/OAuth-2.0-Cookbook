package com.packt.example.simplemvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class SimplemvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplemvcApplication.class, args);
	}

	@GetMapping("/message")
	public ResponseEntity<String> getMessage() {
		return ResponseEntity.ok("Hello!");
	}
}
