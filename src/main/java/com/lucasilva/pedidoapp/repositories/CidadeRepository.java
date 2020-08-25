package com.lucasilva.pedidoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucasilva.pedidoapp.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	@Query(value = "select * from cidade c where c.estado_id = :estadoId", nativeQuery = true)
	public List<Cidade> buscaCidadeDeUmEstado(@Param("estadoId") Long estadoId);
}
