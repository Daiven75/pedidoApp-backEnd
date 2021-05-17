package com.lucasilva.pedidoapp.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="cidade_id")
	private Cidade cidade;

    public Endereco(CepDTO cepDTO) {
    	if(Objects.nonNull(cepDTO)) {
			this.logradouro = cepDTO.getLogradouro();
			this.bairro = cepDTO.getBairro();
			this.cep = cepDTO.getCep();
			this.cidade = new Cidade(null, cepDTO.getLocalidade(), new Estado());
			cidade.getEstado().setSigla(cepDTO.getUf());
		}
    }
}