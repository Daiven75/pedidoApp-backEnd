package com.lucasilva.pedidoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Estado;
import com.lucasilva.pedidoapp.domain.enums.TipoErro;
import com.lucasilva.pedidoapp.repositories.EstadoRepository;
import com.lucasilva.pedidoapp.services.exceptions.EstadoNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> buscaTodosEstados() {
		return estadoRepository.findAllByOrderByNome();
	}

	public Estado buscaEstado(Long estadoId) {
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		if(estado.isEmpty() || estado == null) {
			throw new EstadoNotFoundException(TipoErro.ESTADO_NAO_ENCONTRADO.toString());
		}
		return estado.get();
	}
}