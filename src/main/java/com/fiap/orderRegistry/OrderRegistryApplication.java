package com.fiap.orderRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderRegistryApplication.class, args);
	}

}
