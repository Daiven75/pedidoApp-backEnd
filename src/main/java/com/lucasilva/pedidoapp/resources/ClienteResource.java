package com.lucasilva.pedidoapp.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.dto.ClienteDTO;
import com.lucasilva.pedidoapp.dto.ClienteSaveDTO;
import com.lucasilva.pedidoapp.services.ClienteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@ApiOperation(value = "Busca cliente por id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscaPorId(@PathVariable Long id) {
		Cliente cliente = clienteService.buscaPorId(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@ApiOperation(value = "Busca cliente por email")
	@GetMapping(value = "/email")
	public ResponseEntity<Cliente> findByEmail(@RequestParam(value = "value") String email) {
		Cliente cliente = clienteService.buscaPorEmail(email);
		return ResponseEntity.ok().body(cliente);
	}
	
	@ApiOperation(value = "Cadastra um cliente")
	@PostMapping()
	public ResponseEntity<Void> cadastraCliente(@Valid @RequestBody ClienteSaveDTO clienteSaveDTO) {
		Cliente cliente = clienteService.fromDTO(clienteSaveDTO);
		cliente = clienteService.cadastraCliente(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
	
	@ApiOperation(value = "Atualiza um cliente")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> atualizaCliente(
			@PathVariable Long id,
			@Valid @RequestBody ClienteDTO clienteDTO) {
		clienteService.atualizaCliente(id, clienteDTO);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')") 
	@ApiOperation(value = "Remove um cliente")
	@ApiResponses(value = {
    		@ApiResponse(code = 400, message = "Não é possuir excluir o cliente pois o mesmo possui pedidos"),
    		@ApiResponse(code = 404, message = "Código inexistente") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletaCliente(@PathVariable Long id) {
		clienteService.deletaCliente(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')") 
	@ApiOperation(value = "Busca todos os clientes")
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {
		List<Cliente> listaCliente = clienteService.buscaTodos();
		List<ClienteDTO> listaClienteDTO = listaCliente.stream()
				.map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listaClienteDTO);
	}
	
	@ApiOperation(value = "Salva imagem do cliente")
	@PostMapping(value = "/picture") 
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = clienteService.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}