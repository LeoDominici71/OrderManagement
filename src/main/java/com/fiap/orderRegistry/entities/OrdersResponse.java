package com.fiap.orderRegistry.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter	
public class OrdersResponse {

	
	private Long id;
	private String status;
	private String userName;
	private String zipCode;
	private String city;
	private String district;
	private String street;
	private String complement;
	private String localization;
	private String expectedTimeToDeliver;
}
