package com.fiap.orderRegistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.orderRegistry.entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{
	
	 // Método para buscar pedidos por status usando consulta nativa
    @Query(value = "SELECT * FROM tb_order WHERE status = :status", nativeQuery = true)
    List<Orders> findAllOrdersByStatus(@Param("status") String status);
	

}
