package com.lucasilva.pedidoapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
