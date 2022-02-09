package com.gotgam.bansi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class BansiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BansiApplication.class, args);
	}

}
