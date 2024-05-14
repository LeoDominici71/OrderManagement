package com.fiap.orderRegistry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.ProductResponse;
import com.fiap.orderRegistry.entities.Products;
import com.fiap.orderRegistry.entities.UserDTO;
import com.fiap.orderRegistry.exception.GeneralClientSystemException;
import com.fiap.orderRegistry.service.IntegrationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntegrationServiceImpl implements IntegrationService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDTO getUserDTO(OrderRequest orders) {
		// TODO Auto-generated method stub

		try {

			IntegrationServiceImpl.log.info("IN - user management");

			Map<String, String> uriVariablesUser = new HashMap<>();
			uriVariablesUser.put("id", orders.getUserId().toString());

			String urlUser = "http://localhost:8765/userManagement/api/user/{id}";

			UserDTO userDto = restTemplate.getForObject(urlUser, UserDTO.class, uriVariablesUser);

			IntegrationServiceImpl.log.info("OUT - user management");

			return userDto;

		} catch (Exception e) {
			e.getStackTrace();
			throw new GeneralClientSystemException("Error in accessing user management");
		}
	}

	@Override
	public List<ProductResponse> getProducts(OrderRequest orders) {
		// TODO Auto-generated method stub

		try {
			List<ProductResponse> productResponseList = new ArrayList<>();

			IntegrationServiceImpl.log.info("IN - product registry");

			for (Products product : orders.getProducts()) {
				Long productId = product.getId(); 

				String url = "http://localhost:8765/productRegistry/api/products/{id}";
				Map<String, String> uriVariables = new HashMap<>();
				uriVariables.put("id", productId.toString());

				ProductResponse productResponse = restTemplate.getForObject(url, ProductResponse.class, uriVariables);
				productResponseList.add(productResponse);

			}

			IntegrationServiceImpl.log.info("OUT - product registry");

			return productResponseList;

		} catch (Exception e) {
			
			e.getStackTrace();
			throw new GeneralClientSystemException("Error in accessing user management");

		}

	}

}
