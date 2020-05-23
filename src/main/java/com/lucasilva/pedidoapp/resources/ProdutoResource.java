package com.lucasilva.pedidoapp.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.domain.ProdutoDTO;
import com.lucasilva.pedidoapp.resources.utils.URI;
import com.lucasilva.pedidoapp.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping()
	public ResponseEntity<Page<ProdutoDTO>> buscaPagina(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina,
			@RequestParam(value="ordenaPor", defaultValue="nome") String ordenaPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		
		String nomeDecoded = URI.decodeParam(nome);
		List<Long> listCategorias = URI.decodeLongList(categorias);
		
		Page<Produto> pageProduto = produtoService.buscaPagina(
				nomeDecoded,
				listCategorias,
				pagina, 
				linhasPorPagina,
				ordenaPor,
				direcao);
		Page<ProdutoDTO> pageProdutoDTO = pageProduto.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(pageProdutoDTO);
	}
}
