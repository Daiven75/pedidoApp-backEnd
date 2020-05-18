package com.lucasilva.pedidoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.repositories.ClienteRepository;
import com.lucasilva.pedidoapp.services.exceptions.ClienteNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscaPorId(Long id) {
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);
		return optionalCliente.orElseThrow(
				() -> new ClienteNotFoundException(
						"Cliente com id = " + id + " não encontrado!"));
	}
}
