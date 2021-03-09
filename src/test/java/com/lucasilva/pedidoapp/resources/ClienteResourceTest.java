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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.Endereco;
import com.lucasilva.pedidoapp.domain.Estado;
import com.lucasilva.pedidoapp.domain.enums.TipoCliente;
import com.lucasilva.pedidoapp.dto.ClienteDTO;
import com.lucasilva.pedidoapp.dto.ClienteSaveDTO;
import com.lucasilva.pedidoapp.services.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ClienteResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ClienteService clienteService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Test
    public void getAllClientsWithSucess() throws Exception {
		Cliente cliente1 = new  Cliente(
				2L,
				"nome teste", 
				"email@teste", 
				"42343432", 
				TipoCliente.PESSOAFISICA);
		
		Cliente cliente2 = new  Cliente(
				3L,
				"nome teste", 
				"email@teste", 
				"42343432", 
				TipoCliente.PESSOAFISICA);
		
		doReturn(Lists.newArrayList(cliente1, cliente2)).when(clienteService).buscaTodos();
		
        this.mockMvc.perform(get("/clientes")
        		.with(user("clientTest").password("password").roles("ADMIN"))
        		.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    public void getAllClientsNoPermition() throws Exception {
		
        this.mockMvc.perform(get("/clientes")
        		.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
    }
	
	@Test
    public void getClientByIdWithSucess() throws Exception {
		Cliente cliente1 = new  Cliente(
				2L,
				"nome teste", 
				"email@teste", 
				"42343432", 
				TipoCliente.PESSOAFISICA);
		
		doReturn(cliente1).when(clienteService).buscaPorId(cliente1.getId());
		
        this.mockMvc.perform(get("/clientes/2")
        		.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
        		.andExpect(jsonPath("$.nome").value("nome teste"));
    }
	
	@Test
    public void saveClientWithSucess() throws Exception {
		
		ClienteSaveDTO clienteSaveDTO = new ClienteSaveDTO();
		clienteSaveDTO.setNome("nome teste");
		clienteSaveDTO.setSenha("senha teste");
		clienteSaveDTO.setEmail("email@teste");
		clienteSaveDTO.setCpfOuCnpj("56967734008");
		clienteSaveDTO.setTipoCliente(1);
		clienteSaveDTO.setLogradouro("logradouro teste");
		clienteSaveDTO.setBairro("Renascença");
		clienteSaveDTO.setComplemento("Comercial Araujo");
		clienteSaveDTO.setNumero("488");
		clienteSaveDTO.setCep("5656022");
		clienteSaveDTO.setTelefone("55987234886");
		clienteSaveDTO.setTelefone2("55987234886");
		clienteSaveDTO.setTelefone3("55987234886");
		clienteSaveDTO.setCidadeId(1L);
		
		Cliente cliente = new Cliente(
				2L, 
				"Lucas", 
				passwordEncoder.encode("senhaQualquer"),
				"75.lucas.slima@gmail.com", 
				"67383494102", 
				TipoCliente.PESSOAFISICA);
		cliente.getTelefones().addAll(Arrays.asList("989341021", "9898313410"));
		
		Estado estado = new Estado(null, "Maranhão");
		Cidade cidade = new Cidade(null, "Imperatriz", estado);
		Endereco endereco = new Endereco(
				null, 
				"avenida sao vicente", 
				"82", 
				"Center TemTudo", 
				"Santa rosa", 
				"65054312", 
				cliente, 
				cidade);
		cliente.getEnderecos().addAll(Arrays.asList(endereco));
		
		doReturn(cliente).when(clienteService).fromDTO(clienteSaveDTO);
		doReturn(cliente).when(clienteService).cadastraCliente(any());

        this.mockMvc.perform(post("/clientes")
        		.content(asJsonString(clienteSaveDTO))
        		.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/clientes/2"))
				.andReturn();
    }
	
	@Test
	public void getClientByEmail() throws Exception {
		
		Cliente cliente1 = new  Cliente(
				2L,
				"nome teste", 
				"email@teste", 
				"42343432", 
				TipoCliente.PESSOAFISICA);
		
		doReturn(cliente1).when(clienteService).buscaPorEmail(cliente1.getEmail());
		
		this.mockMvc.perform(get("/clientes/email")
				.param("value", "email@teste")
				.with(user("clientTest").password("password").roles("ADMIN")))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(2)))
			.andExpect(jsonPath("$.nome", is("nome teste")));
	}
	
	@Test
	public void updateClientWithSucess() throws Exception {
		
		Cliente cliente1 = new  Cliente();
		
		ClienteDTO atualizaCliente = new  ClienteDTO(
				"nome teste update", 
				"emailUpdate@teste");
		
		doReturn(cliente1).when(clienteService).atualizaCliente(2L, atualizaCliente);
		
		this.mockMvc.perform(put("/clientes/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(atualizaCliente))
				.with(user("clientTest").password("password").roles("ADMIN")))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteClientById() throws Exception {
		
		Cliente cliente1 = new  Cliente(
				2L,
				"nome teste", 
				"email@teste", 
				"42343432", 
				TipoCliente.PESSOAFISICA);

		doNothing().when(clienteService).deletaCliente(cliente1.getId());
		
		this.mockMvc.perform(delete("/clientes/2")
				.with(user("clientTest").password("password").roles("ADMIN")))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void uploadProfilePictureWithSucess() throws Exception {
		
		MockMultipartFile file = new MockMultipartFile("file", "anything".getBytes());
		
		URI uri = new URI("uri");
		doReturn(uri).when(clienteService).uploadProfilePicture(file);
		
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/clientes/picture")
				.file(file)
				.with(user("clientTest").password("password").roles("ADMIN")))
		.andExpect(status().isCreated());
	}
	
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
