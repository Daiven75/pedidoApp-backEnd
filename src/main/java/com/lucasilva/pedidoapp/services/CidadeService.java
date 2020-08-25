package com.lucasilva.pedidoapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	public List<Cidade> buscaCidadeDeUmEstado(Long estadoId) {
		estadoService.buscaEstado(estadoId);
		return cidadeRepository.buscaCidadeDeUmEstado(estadoId);
	}
}
