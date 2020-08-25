package com.lucasilva.pedidoapp.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasilva.pedidoapp.config.SpringSecurityWebAuxTestConfig;
import com.lucasilva.pedidoapp.config.TestConfig;
import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.enums.TipoCliente;
import com.lucasilva.pedidoapp.services.ClienteService;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "75.lucas.slima@gmail.com", roles = {"USER", "ADMIN"})
public class ClienteResourceTest {

//	@LocalServerPort
//	private int port;
//	
//	@Autowired
//	private TestRestTemplate restTemplate;
//	
//	@Mock
//	private ClienteService clienteService;
//	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
	
	@InjectMocks
	ClienteResource clienteResource;
	
	@Mock
	ClienteService clienteService;
	
	@Test
	@WithUserDetails("75.lucas.slima2@gmail.com")
	public void getByIdClienteComSucesso() throws Exception {

		Cliente cliente = new  Cliente(
				2L,
				"nome teste", 
				"email@teste", 
				"42343432", 
				TipoCliente.PESSOAFISICA);
		
		when(clienteService.buscaPorId(cliente.getId())).thenReturn(cliente);

		this.mockMvc.perform(get("/clientes/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cliente))
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
//		this.mockMvc.perform(get("cliente/{id}")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

//		
//		ResponseEntity<Cliente> response = restTemplate
//				.getForEntity("http://localhost" + port + "/{id}", Cliente.class);
//		assertEquals(cliente, response.getBody());
	}
}
