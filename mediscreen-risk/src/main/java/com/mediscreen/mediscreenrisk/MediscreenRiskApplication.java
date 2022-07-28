package com.mediscreen.mediscreenrisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.mediscreenrisk")
public class MediscreenRiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenRiskApplication.class, args);
	}

}
