package com.fiap.orderRegistry.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.entities.Orders;
import com.fiap.orderRegistry.entities.ProductResponse;
import com.fiap.orderRegistry.entities.UserDTO;
import com.fiap.orderRegistry.factory.Factory;
import com.fiap.orderRegistry.repository.OrderRepository;
import com.fiap.orderRegistry.service.impl.OrderServiceImpl;

@SpringBootTest
public class ServiceTest {

	@InjectMocks
	private OrderServiceImpl service;
	
	@Mock
	private IntegrationService integration;

	@Mock
	private OrderRepository repository;


	@Test
	public void testServiceRegisterOrder() {
		// Arrange

		UserDTO mockUserDto = new UserDTO();
		mockUserDto.setId(1L);
		mockUserDto.setNome("User Name");
		mockUserDto.setCidade("Praia Grande");
		mockUserDto.setCep("11701620");
		mockUserDto.setBairro("Guilhermina");
		

		// Simular resposta do produto
		ProductResponse mockProductResponse = new ProductResponse();
		mockProductResponse.setId(1L);
		mockProductResponse.setName("Mouse");
		mockProductResponse.setPrice(20.0);
		mockProductResponse.setDescription("mouse");
		

		Orders order = Factory.createOrder();
		OrderRequest request = Factory.createOrderRequest();
		
		List<ProductResponse> response = Factory.listProducts();
		
		Mockito.when(integration.getUserDTO(request)).thenReturn(mockUserDto);
		Mockito.when(integration.getProducts(request)).thenReturn(response);


		// Act
		Mockito.when(repository.save(ArgumentMatchers.any(Orders.class))).thenReturn(order);
		Orders result = service.registerOrder(request);
		// Assert
		assertNotNull(result);
		assertEquals(order.getZipCode(), result.getZipCode());

	}
	
	@Test
	public void testServiceDeleteOrder() {
		// Arrange
		Optional<Orders> order = Optional.of(Factory.createOrder());

		Mockito.when(repository.findById(1L)).thenReturn(order);


		// Act
		Assertions.assertDoesNotThrow(() -> {
			service.deleteOrder(1L);
		});


	}
	
	@Test
	public void testUpdateOrder() {
		// Arrange
		Orders orders = Factory.createOrder();
		Optional<Orders> order = Optional.of(Factory.createOrder());
		OrderRequestUpdate request = Factory.createOrderRequestUpdate();

		// Act
		Mockito.when(repository.findById(1L)).thenReturn(order);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(orders);

		Orders result = service.updateOrder(1L, request);
		
		//Assert
		assertNotNull(result);
		verify(repository, Mockito.times(1)).findById(1L);

	}
	

}
