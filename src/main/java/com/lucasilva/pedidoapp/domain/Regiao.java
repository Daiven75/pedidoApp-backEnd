package com.lucasilva.pedidoapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Regiao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sigla;
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "regiao")
    private List<Estado> estados = new ArrayList<>();
}