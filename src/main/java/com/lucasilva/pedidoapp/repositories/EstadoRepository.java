package com.lucasilva.pedidoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasilva.pedidoapp.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

	public List<Estado> findAllByOrderByNome();
}
