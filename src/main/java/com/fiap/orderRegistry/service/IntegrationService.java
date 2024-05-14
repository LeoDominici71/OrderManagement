package com.fiap.orderRegistry.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.ProductResponse;
import com.fiap.orderRegistry.entities.UserDTO;

@Service
public interface IntegrationService {

	
	UserDTO getUserDTO(OrderRequest orders);
	
	List<ProductResponse> getProducts(OrderRequest orders);
	
}
