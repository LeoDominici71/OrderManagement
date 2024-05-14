package com.fiap.orderRegistry.controllerIT;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.factory.Factory;
import com.fiap.orderRegistry.repository.OrderRepository;
import com.fiap.orderRegistry.service.OrderService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderRegistryController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;


	@MockBean
	private OrderService orderService;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveRegistrarUmaOrdem() throws Exception {

		Mockito.when(orderService.registerOrder(Mockito.any(OrderRequest.class))).thenReturn(Factory.createOrder());

		OrderRequest request = Factory.createOrderRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

		ResultActions response = mockMvc
				.perform(post("/api/order").content(jsonBody).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isCreated());
		response.andExpect(jsonPath("$.zipCode").exists());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveDeletarOrderPorId() throws Exception {

		// Act
		Assertions.assertDoesNotThrow(() -> {
			orderService.deleteOrder(1L);
		});

		ResultActions response = mockMvc
				.perform(delete("/api/order/deleteOrder/{id}", 1L).accept(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isNoContent());

	}
	

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveAtualizarUmaOrdem() throws Exception {
		// Arrange
		OrderRequestUpdate request = Factory.createOrderRequestUpdate();
		String jsonBody = objectMapper.writeValueAsString(request);

		// Act
		Mockito.when(orderService.updateOrder(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Factory.createOrder());

		// Act
		ResultActions response = mockMvc
				.perform(put("/api/order/atualiza/{id}", 1L).content(jsonBody).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk());

	}

}
