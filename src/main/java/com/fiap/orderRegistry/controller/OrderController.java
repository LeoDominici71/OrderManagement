package com.fiap.orderRegistry.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.OrderRequestPayment;
import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.entities.Orders;
import com.fiap.orderRegistry.entities.OrdersResponse;
import com.fiap.orderRegistry.service.OrderService;
import com.fiap.orderRegistry.utils.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/order")
@Slf4j
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Orders>> listOrders() {
		List<Orders> ordersList = service.getAllOrders();
		OrderController.log.info("OUT - FindOrdersList");
		return ResponseEntity.ok().body(ordersList);

	}

	@GetMapping(value = "/ordersByStatus")
	public ResponseEntity<List<Orders>> listOrdersByStatus(@RequestParam String status) {
		List<Orders> ordersList = service.getAllOrdersByStatus(status);
		OrderController.log.info("OUT - FindOrdersListByStatus");
		return  ResponseEntity.ok().body(ordersList);

	}
	
	@PostMapping
	public ResponseEntity<Orders> orderRegistry(@RequestBody OrderRequest orders){
		Orders ordersRegistered = service.registerOrder(orders);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(ordersRegistered.getId()).toUri();
		OrderController.log.info("OUT - OrdersRegistered");
		return ResponseEntity.created(uri).body(ordersRegistered);

	}
	
	@PostMapping("/saveAll")
	public ResponseEntity<?> saveAllOrders(@RequestBody List<Orders> orders){
		service.saveAllOrders(orders);
		OrderController.log.info("OUT - OrdersSaveAll");
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/atualiza/{id}")
	public ResponseEntity<Orders> orderUpdate(@RequestBody OrderRequestUpdate orders, @PathVariable Long id){
		Orders ordersUpdated = service.updateOrder(id, orders);
		OrderController.log.info("OUT - OrdersUpdated");
		return ResponseEntity.ok().body(ordersUpdated);

	}
	
	@PutMapping(value = "/payment/{id}")
	public ResponseEntity<Orders> orderPayment(@RequestBody OrderRequestPayment orders, @PathVariable Long id){
		Orders ordersUpdated = service.payOrder(id, orders);
		OrderController.log.info("OUT - OrdersPaid");
		return ResponseEntity.ok().body(ordersUpdated);

	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdersResponse> orderById(@PathVariable Long id){
		Orders ordersUpdated = service.getOrderById(id);
		OrderController.log.info("OUT - OrdersById");
		return ResponseEntity.ok().body(ApplicationUtils.toOrdersResponse(ordersUpdated));

	}
	
	@DeleteMapping(value = "/deleteOrder/{id}")
	public ResponseEntity<?> deleteOrder(Long id) {
		service.deleteOrder(id);
		OrderController.log.info("OUT - DeleteOrder");
		return ResponseEntity.noContent().build();
	}

}
