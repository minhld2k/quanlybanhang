package com.DuAnJV;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DuAnJvApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuAnJvApplication.class, args);
	}
	
}
