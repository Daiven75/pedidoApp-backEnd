package com.lucasilva.pedidoapp.resources;

import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.domain.Endereco;
import com.lucasilva.pedidoapp.domain.Estado;
import com.lucasilva.pedidoapp.dto.CidadeDTO;
import com.lucasilva.pedidoapp.dto.EstadoDTO;
import com.lucasilva.pedidoapp.services.CidadeService;
import com.lucasilva.pedidoapp.services.EstadoService;
import com.lucasilva.pedidoapp.utils.RequestUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private RequestUtils requestUtils;
	
	@ApiOperation(value = "Busca todos os estados")
	@Cacheable(value = "estados")
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> buscaTodosEstados() {
		List<Estado> estados = estadoService.buscaTodosEstados();
		List<EstadoDTO> estadosDTO = new ArrayList<>();
		estados.forEach(estado -> estadosDTO.add(new EstadoDTO(estado)));
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@ApiOperation(value = "Busca todas as cidades de um estado")
	@Cacheable(value = "cidades")
	@GetMapping(value = "/{estadoId}/cidades") 
	public ResponseEntity<List<CidadeDTO>> buscaCidadesEmUmEstado(@PathVariable Long estadoId) {
		List<Cidade> cidades = cidadeService.buscaCidadeDeUmEstado(estadoId);
		List<CidadeDTO> cidadesDTO = new ArrayList<>();
		cidades.forEach(cidade -> cidadesDTO.add(new CidadeDTO(cidade)));
		return ResponseEntity.ok().body(cidadesDTO);
	}

	@GetMapping("/cep/{cep}")
	public ResponseEntity<Endereco> buscarCep(@PathVariable String cep) {
		return ResponseEntity.ok(requestUtils.getRequestCep(cep));
	}
}