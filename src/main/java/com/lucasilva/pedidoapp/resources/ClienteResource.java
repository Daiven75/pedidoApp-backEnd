package com.lucasilva.pedidoapp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscarCliente(@PathVariable Long id) {
		Cliente cliente = clienteService.buscarCliente(id);
		return ResponseEntity.ok().body(cliente);
	}
}
