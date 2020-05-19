package com.lucasilva.pedidoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.ClienteDTO;
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

	public Cliente atualizaCliente(Cliente atualizaCliente) {
		Cliente cliente = buscaPorId(atualizaCliente.getId());
		atualizaDados(cliente, atualizaCliente);
		return clienteRepository.save(cliente);
	}

	public List<Cliente> buscaTodos() {
		return clienteRepository.findAll();
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void atualizaDados(Cliente cliente, Cliente atualizaCliente) {
		cliente.setNome(atualizaCliente.getNome());
		cliente.setEmail(atualizaCliente.getEmail());
	}
}
