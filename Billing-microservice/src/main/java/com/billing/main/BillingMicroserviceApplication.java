package com.billing.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan(basePackages = { "com.billing.main.model", "com.inventory.main.model" })
@ComponentScan(basePackages = {"com.billing.main", "com.inventory.main"})
@EnableDiscoveryClient
public class BillingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingMicroserviceApplication.class, args);
	}
	
	

}
