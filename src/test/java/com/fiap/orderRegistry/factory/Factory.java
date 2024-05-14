package com.fiap.orderRegistry.factory;

import java.util.ArrayList;
import java.util.List;

import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.entities.Orders;
import com.fiap.orderRegistry.entities.ProductResponse;
import com.fiap.orderRegistry.entities.Products;

public class Factory {

	public static OrderRequest createOrderRequest() {
		OrderRequest request = new OrderRequest();
		Products products = new Products();
		products.setId(1L);
		products.setName("Mouse");
		List<Products> listProducts = new ArrayList<>();
		listProducts.add(products);
		request.setUserId(1L);
		request.setProducts(listProducts);
		return request;
	}
	
	public static OrderRequest createOrderRequestWithNullId() {
		OrderRequest request = new OrderRequest();
		Products products = new Products();
		products.setId(1L);
		products.setName("Mouse");
		List<Products> listProducts = new ArrayList<>();
		listProducts.add(products);
		request.setProducts(listProducts);
		return request;
	}

	public static Orders createOrder() {
		Orders response = new Orders();
		Products products = new Products();
		products.setId(1L);
		products.setName("Mouse");
		products.setDescription("mouse");
		products.setPrice(20.0);
		List<Products> listProducts = new ArrayList<>();
		listProducts.add(products);
		response.setUserId(1L);
		response.setUserName("User Name");
		response.setCity("Praia Grande");
		response.setStatus("solicitado");
		response.setDistrict("Guilhermina");
		response.setZipCode("11701620");
		response.setProducts(listProducts);
		return response;
	}
	
	public static OrderRequestUpdate createOrderRequestUpdate() {
		OrderRequestUpdate request = new OrderRequestUpdate();
		request.setStatus("Confirmado");
		return request;
	}
	
	
	public static List<ProductResponse> listProducts(){
		List<ProductResponse> productResponseList = new ArrayList<>();
		ProductResponse product = new ProductResponse();
		product.setId(1L);
		product.setDescription("mouse");
		product.setName("moouse");
		product.setPrice(20.0);
		product.setStock("2");
		
		productResponseList.add(product);
		
		return productResponseList;
	}

}
