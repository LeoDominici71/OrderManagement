package com.fiap.orderRegistry.controllerIT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderRegistryControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void naoDeveRegistrarUmaOrdemComVariaveisNula() throws Exception {
		// Arrange
		OrderRequest request = Factory.createOrderRequestWithNullId();
		String jsonBody = objectMapper.writeValueAsString(request);

		// Act
		ResultActions response = mockMvc
				.perform(post("/api/order").content(jsonBody).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isUnprocessableEntity());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveBuscarOrderPorId() throws Exception {

		// Arrange
		orderRepository.save(Factory.createOrder());

		// Act
		ResultActions response = mockMvc.perform(get("/api/order/{Id}", 1L).accept(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk());
		response.andExpect(jsonPath("$.zipCode").exists());

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void naoDeveBuscarOrderPorIdQuandoOIdNaoExiste() throws Exception {

		// Act
		ResultActions response = mockMvc.perform(get("/api/order/{Id}", 3L).accept(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isUnprocessableEntity());

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveRetornarListaDeOrders() throws Exception {

		// Arrange
		orderRepository.save(Factory.createOrder());

		// Act
		ResultActions response = mockMvc.perform(get("/api/order").accept(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk());
		response.andExpect(jsonPath("$[0].zipCode").exists());

	}

	@Test
	public void deveriaEncontrarUmPedidoPorStatusPorNome() throws Exception {
		// Arrange
		String status = "Confirmed";

		// Act
		ResultActions response = mockMvc.perform(
				get("/api/order/ordersByStatus").param("status", status).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk());

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void naoDeveDeletarOrderPorIdQuandoIdNaoExiste() throws Exception {

		ResultActions response = mockMvc
				.perform(delete("/api/order/deleteOrder/{id}", 10L).accept(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isUnprocessableEntity());

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveAtualizarUmaOrdem() throws Exception {
		// Arrange
		OrderRequestUpdate request = Factory.createOrderRequestUpdate();
		String jsonBody = objectMapper.writeValueAsString(request);

		// Act
		ResultActions response = mockMvc.perform(
				put("/api/order/atualiza/{id}", 10L).content(jsonBody).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isUnprocessableEntity());

	}

}