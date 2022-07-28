package com.mediscreen.mediscreenregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.mediscreenregister")
public class MediscreenRegistryApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediscreenRegistryApplication.class, args);
	}

}
