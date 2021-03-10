package com.lucasilva.pedidoapp.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.dto.CategoriaDTO;
import com.lucasilva.pedidoapp.services.CategoriaService;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class CategoriaResourceTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CategoriaService categoriaService;
	
	@Test
	public void getCategoriaByIdWithSucess() throws Exception {
		Categoria categoria = new Categoria(5L, "categoria teste");
		
		doReturn(categoria).when(categoriaService).buscaPorId(5L);
		
		this.mockMvc.perform(get("/categorias/5"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("categoria teste")));
	}
	
	@Test
	public void getAllCategoriasWithSucess() throws Exception {
		Categoria categoria1 = new Categoria(5L, "categoria teste");
		Categoria categoria2 = new Categoria(6L, "categoria teste");
		
		doReturn(Lists.newArrayList(categoria1, categoria2)).when(categoriaService).buscaTodos();
		
		this.mockMvc.perform(get("/categorias"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[1].id", is(6)));
	}
	
	@Test
	public void saveCategoriaWithSucess() throws Exception {
		Categoria categoria = new Categoria(5L, "categoria teste");
		CategoriaDTO categoriaDTO = new CategoriaDTO(categoria);
		
		doReturn(categoria).when(categoriaService).cadastraCategoria(any());
		
		this.mockMvc.perform(post("/categorias")
				.content(asJsonString(categoriaDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.with(user("clientTest").password("password").roles("ADMIN")))
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/categorias/5"))
			.andReturn();
	}
	
	@Test
	public void updateCategoriaWithSucess() throws Exception {
		Categoria categoria = new Categoria(5L, "");
		CategoriaDTO categoriaDTO = new CategoriaDTO("categoria teste");
		
		doReturn(categoria).when(categoriaService).atualizaCategoria(5L, categoriaDTO);
		
		this.mockMvc.perform(put("/categorias/5")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(categoriaDTO))
				.with(user("clientTest").password("password").roles("ADMIN")))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteCategoriaById() throws Exception {
		Categoria categoria = new Categoria(5L, "categoria teste");

		doNothing().when(categoriaService).deletaCategoria(categoria.getId());
		
		this.mockMvc.perform(delete("/categorias/5")
				.with(user("clientTest").password("password").roles("ADMIN")))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void getPageByCategoriaWithSucess() throws Exception {
		Categoria categoria1 = new Categoria(5L, "categoria teste");
		Categoria categoria2 = new Categoria(6L, "categoria teste");
		
		PageRequest pageRequest = PageRequest.of(5, 24, Direction.valueOf("ASC"), "nome");
		List<Categoria> listCategoria = Lists.newArrayList(categoria1, categoria2);
		Page<Categoria> pageCategoria = new PageImpl<Categoria>(listCategoria, pageRequest, listCategoria.size());
		
		doReturn(pageCategoria).when(categoriaService).buscaPagina(5, 24, "nome", "ASC");
		
		this.mockMvc.perform(get("/categorias/page")
				.contentType(MediaType.APPLICATION_JSON)
				.param("pagina", "5")
				.param("linhasPorPagina", "24")
				.param("ordenaPor", "nome")
				.param("direcao", "ASC"))
		.andExpect(status().isOk());
	}
	
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
