package com.lucasilva.pedidoapp.resources;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.services.PedidoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@ApiOperation(value = "Busca um pedido por id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> buscaPorId(@PathVariable Long id) {
		Pedido pedido = pedidoService.buscaPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(pedido);
	}
	
	@ApiOperation(value = "Cadastra um pedido")
	@PostMapping()
	public ResponseEntity<Void> cadastraPedido(@Valid @RequestBody Pedido pedido) {
		pedido = pedidoService.cadastraPedido(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
	
	@ApiOperation(value = "Busca todos os pedidos com paginação")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<Pedido>> buscaPagina(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina,
			@RequestParam(value="ordenaPor", defaultValue="instante") String ordenaPor, 
			@RequestParam(value="direcao", defaultValue="DESC") String direcao) {
		
		Page<Pedido> pageRequest = pedidoService.buscaPagina(pagina, linhasPorPagina, ordenaPor, direcao);
		return ResponseEntity.ok().body(pageRequest);
	}
}
