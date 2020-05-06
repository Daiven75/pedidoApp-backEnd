package com.lucasilva.pedidoapp;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.repositories.CategoriaRepository;
import com.lucasilva.pedidoapp.repositories.ProdutoRepository;

@SpringBootApplication
public class PedidoappApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PedidoappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Móveis");
		
		Produto p1 = new Produto(null, "Notebook", 3550.50);
		Produto p2 = new Produto(null, "Mouse", 120.00);
		Produto p3 = new Produto(null, "Rack", 420.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2));
		cat2.getProdutos().addAll(Arrays.asList(p3));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2 ,p3));
	}
}