package com.lucasilva.pedidoapp.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> buscaPorId(@PathVariable Long id) {
		Pedido pedido = pedidoService.buscaPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(pedido);
	}
	
	@PostMapping()
	public ResponseEntity<Void> cadastraCategoria(@Valid @RequestBody Pedido pedido) {
		pedido = pedidoService.cadastraPedido(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
}
