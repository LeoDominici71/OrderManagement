package com.fiap.orderRegistry.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestPayment {

	@NotNull
	private String cardNumber;

}
