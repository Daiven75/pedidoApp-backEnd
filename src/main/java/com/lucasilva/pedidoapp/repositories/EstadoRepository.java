package com.lucasilva.pedidoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasilva.pedidoapp.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

}
