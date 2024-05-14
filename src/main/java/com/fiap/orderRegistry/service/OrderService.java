package com.fiap.orderRegistry.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.orderRegistry.entities.OrderRequest;
import com.fiap.orderRegistry.entities.OrderRequestUpdate;
import com.fiap.orderRegistry.entities.Orders;

@Service
public interface OrderService {

	Orders registerOrder(OrderRequest orders);

	Orders getOrderById(Long id);

	void deleteOrder(Long id);

	Orders updateOrder(Long id, OrderRequestUpdate order);

	List<Orders> getAllOrders();

	List<Orders> getAllOrdersByStatus(String status);

}
