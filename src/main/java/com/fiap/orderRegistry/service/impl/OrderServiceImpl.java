package com.fiap.orderRegistry.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiap.orderRegistry.constants.ApplicationConstants;
import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.entities.Orders;
import com.fiap.orderRegistry.entities.ProductResponse;
import com.fiap.orderRegistry.entities.UserDTO;
import com.fiap.orderRegistry.exception.GeneralClientSystemException;
import com.fiap.orderRegistry.repository.OrderRepository;
import com.fiap.orderRegistry.service.IntegrationService;
import com.fiap.orderRegistry.service.OrderService;
import com.fiap.orderRegistry.utils.ApplicationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final IntegrationService integration;
	private final OrderRepository repository;

	public OrderServiceImpl(OrderRepository repository, IntegrationService integration) {
		this.repository = repository;
		this.integration = integration;
	}

	@Override
	public Orders registerOrder(OrderRequest orders) {
		// TODO Auto-generated method stub

		try {
			OrderServiceImpl.log.info("IN - Register Order");
			
			UserDTO userDto = integration.getUserDTO(orders);
			List<ProductResponse> productResponseList = integration.getProducts(orders);

			Orders statusConfirmed = ApplicationUtils.toOrders(userDto, productResponseList);
			statusConfirmed.setStatus(ApplicationConstants.SOLICITADO);

			Orders orderSaved = repository.save(statusConfirmed);

			return orderSaved;

		} catch (Exception e) {
			throw new GeneralClientSystemException("Error in register order");
		}
	}

	@Override
	public Orders getOrderById(Long id) {
		// TODO Auto-generated method stub
		OrderServiceImpl.log.info("IN - Get Order by Id");

		try {
			Optional<Orders> request = repository.findById(id);
			Orders order = request.orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
			return order;
		} catch (Exception e) {
			throw new GeneralClientSystemException("Error in getting order by id");
		}
	}

	@Override
	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub
		OrderServiceImpl.log.info("IN - Delete Order by Id");

		try {
			Optional<Orders> request = repository.findById(id);
			Orders order = request.orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

			repository.delete(order);

		} catch (Exception e) {
			throw new IllegalArgumentException("Error in deleting order");
		}
	}

	@Override
	public Orders updateOrder(Long id, OrderRequestUpdate order) {
		// TODO Auto-generated method stub
		OrderServiceImpl.log.info("IN - Update Order by Id");

		try {

			Optional<Orders> request = repository.findById(id);
			Orders orderToUpdate = request.orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
			Orders orderSaved = repository.save(ApplicationUtils.updateOrder(orderToUpdate, order));
			return orderSaved;

		} catch (Exception e) {
			throw new IllegalArgumentException("Error in updating order");
		}
	}

	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		OrderServiceImpl.log.info("IN - Get All Orders");

		return repository.findAll();
	}

	@Override
	public List<Orders> getAllOrdersByStatus(String status) {
		// TODO Auto-generated method stub
		OrderServiceImpl.log.info("IN - Get Order by Status");

		return repository.findByStatus(status);
	}

}
