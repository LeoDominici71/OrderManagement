package com.fiap.orderRegistry.entities;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

	@NotNull
	private Long userId;
	@NotNull
	private List<Products> products;


}
