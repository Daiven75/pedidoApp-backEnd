package com.lucasilva.pedidoapp.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.services.validation.ClienteUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ClienteUpdate
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Length(min = 5, max = 120, message = "Tamanho deve conter entre 5 a 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email Inválido")
	private String email;
	
	public ClienteDTO(Cliente cliente) {
		nome = cliente.getNome();
		email = cliente.getEmail();
	}
}
