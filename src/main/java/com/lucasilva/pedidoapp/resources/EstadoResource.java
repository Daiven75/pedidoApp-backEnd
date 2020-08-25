package com.lucasilva.pedidoapp.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.domain.Estado;
import com.lucasilva.pedidoapp.dto.CidadeDTO;
import com.lucasilva.pedidoapp.dto.EstadoDTO;
import com.lucasilva.pedidoapp.services.CidadeService;
import com.lucasilva.pedidoapp.services.EstadoService;

@RestController()
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> buscaTodosEstados() {
		List<Estado> estados = estadoService.buscaTodosEstados();
		List<EstadoDTO> estadosDTO = new ArrayList<>();
		estados.stream().forEach(estado -> estadosDTO.add(new EstadoDTO(estado)));
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@GetMapping(value = "/{estadoId}/cidades") 
	public ResponseEntity<List<CidadeDTO>> buscaCidadesEmUmEstado(@PathVariable Long estadoId) {
		List<Cidade> cidades = cidadeService.buscaCidadeDeUmEstado(estadoId);
		List<CidadeDTO> cidadesDTO = new ArrayList<>();
		cidades.stream().forEach(cidade -> cidadesDTO.add(new CidadeDTO(cidade)));
		return ResponseEntity.ok().body(cidadesDTO);
	}
}
