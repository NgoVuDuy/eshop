package com.nvd.electroshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ElectroshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectroshopApplication.class, args);
	}

}