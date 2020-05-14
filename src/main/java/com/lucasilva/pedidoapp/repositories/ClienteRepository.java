package com.lucasilva.pedidoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasilva.pedidoapp.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
