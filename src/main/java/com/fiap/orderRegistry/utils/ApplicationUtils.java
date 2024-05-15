package com.fiap.orderRegistry.utils;

import java.util.ArrayList;
import java.util.List;

import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.entities.Orders;
import com.fiap.orderRegistry.entities.ProductResponse;
import com.fiap.orderRegistry.entities.Products;
import com.fiap.orderRegistry.entities.UserDTO;

public class ApplicationUtils {

	public static Orders updateOrder(Orders orderAntigo, OrderRequestUpdate orderAtual) {
		if (orderAtual.getStatus() != null) {
			orderAntigo.setStatus(orderAtual.getStatus());
		}
		if (orderAtual.getExpectedTimeToDeliver() != null) {
			orderAntigo.setExpectedTimeToDeliver(orderAtual.getExpectedTimeToDeliver());
		}
		
		return orderAntigo;
	}

	public static Orders toOrders(UserDTO userDTO, List<ProductResponse> products) {
		Orders orders = new Orders();

		List<Products> convertedProducts = new ArrayList<>();
		for (ProductResponse productResponse : products) {
			Products product = new Products();
			product.setProductCode(productResponse.getId());
			product.setName(productResponse.getName());
			product.setDescription(productResponse.getDescription());
			product.setStock(productResponse.getStock());
			product.setPrice(productResponse.getPrice());
			convertedProducts.add(product);
		}

		orders.setUserName(userDTO.getNome());
		orders.setCity(userDTO.getCidade());
		orders.setZipCode(userDTO.getCep());
		orders.setDistrict(userDTO.getBairro());
		orders.setStreet(userDTO.getRua());
		orders.setComplement(userDTO.getComplemento());
		orders.setUserId(userDTO.getId());
		orders.setProducts(convertedProducts);

		return orders;

	}

}
