package com.fiap.orderRegistry.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestUpdate {

	private String status;
	private String expectedTimeToDeliver;

}
