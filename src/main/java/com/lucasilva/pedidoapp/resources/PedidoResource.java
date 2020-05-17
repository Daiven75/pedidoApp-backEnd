package com.lucasilva.pedidoapp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getPedidoId(@PathVariable Long id) {
		Pedido pedido = pedidoService.getPedidoId(id);
		return ResponseEntity.status(HttpStatus.OK).body(pedido);
	}
}
