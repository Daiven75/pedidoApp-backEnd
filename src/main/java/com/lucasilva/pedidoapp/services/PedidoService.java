package com.lucasilva.pedidoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.repositories.PedidoRepository;
import com.lucasilva.pedidoapp.services.exceptions.PedidoNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscaPorId(Long id) {
		Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
		return optionalPedido.orElseThrow(
				() -> new PedidoNotFoundException(
						"Pedido de id = " + id + " não encontrado!"));
	}
}
