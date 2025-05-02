package com.vanrides.vanrides;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import at.favre.lib.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class VanridesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VanridesApplication.class, args);
	}

	
}
