package com.fiap.orderRegistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.orderRegistry.entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{
	
	List<Orders> findByStatus(String status);
	

}
