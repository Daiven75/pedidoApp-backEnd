package com.lucasilva.pedidoapp.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.ClienteDTO;
import com.lucasilva.pedidoapp.domain.ClienteSaveDTO;
import com.lucasilva.pedidoapp.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscaPorId(@PathVariable Long id) {
		Cliente cliente = clienteService.buscaPorId(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@PostMapping()
	public ResponseEntity<Void> cadastraCliente(@Valid @RequestBody ClienteSaveDTO clienteSaveDTO) {
		Cliente cliente = clienteService.fromDTO(clienteSaveDTO);
		cliente = clienteService.cadastraCliente(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> atualizaCliente(
			@PathVariable Long id,
			@Valid @RequestBody ClienteDTO ClienteDTO) {
		Cliente Cliente = clienteService.fromDTO(ClienteDTO);
		Cliente.setId(id);
		Cliente = clienteService.atualizaCliente(Cliente);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletaCliente(@PathVariable Long id) {
		clienteService.deletaCliente(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {
		List<Cliente> listaCliente = clienteService.buscaTodos();
		List<ClienteDTO> listaClienteDTO = listaCliente.stream()
				.map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listaClienteDTO);
	}
}
