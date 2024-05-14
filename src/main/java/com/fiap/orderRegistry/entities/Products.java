package com.fiap.orderRegistry.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_products")
public class Products {
	@Id
	private Long id;
	private String name;
	private String description;
	private String stock;
	private Double price;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;

}
