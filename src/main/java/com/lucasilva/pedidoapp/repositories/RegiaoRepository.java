package com.lucasilva.pedidoapp.repositories;

import com.lucasilva.pedidoapp.domain.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long>{

}