package com.coxplore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CoxploreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoxploreApplication.class, args);
	}

	@GetMapping("/")
	public String mainPage() {
		return "Hello World!";
	}

}
