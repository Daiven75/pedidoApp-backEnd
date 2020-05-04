package com.lucasilva.pedidoapp.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasilva.pedidoapp.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@GetMapping
	public List<Categoria> listarCategorias() {
		Categoria c1 = new Categoria(1L, "Móveis");
		Categoria c2 = new Categoria(2L, "Eletrônicos");
		List<Categoria> listagemCategoria = new ArrayList<>();
		listagemCategoria.add(c1);
		listagemCategoria.add(c2);
		return listagemCategoria;
	}
}
