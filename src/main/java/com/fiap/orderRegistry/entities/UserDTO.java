package com.fiap.orderRegistry.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	
	
    private Long id;
	private String nome;
	private String cpf;
	private String email;
	private Integer idade;
	private String cep;
	private String cidade;
	private String bairro;
	private String rua;
	private String numero;
	private String complemento;

	

}
