package com.lucasilva.pedidoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.Endereco;
import com.lucasilva.pedidoapp.domain.enums.Perfil;
import com.lucasilva.pedidoapp.domain.enums.TipoCliente;
import com.lucasilva.pedidoapp.dto.ClienteDTO;
import com.lucasilva.pedidoapp.dto.ClienteSaveDTO;
import com.lucasilva.pedidoapp.repositories.ClienteRepository;
import com.lucasilva.pedidoapp.repositories.EnderecoRepository;
import com.lucasilva.pedidoapp.security.UserSS;
import com.lucasilva.pedidoapp.services.exceptions.AuthorizationException;
import com.lucasilva.pedidoapp.services.exceptions.ClienteNotFoundException;
import com.lucasilva.pedidoapp.services.exceptions.DataIntegrityException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Cliente buscaPorId(Long id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);
		return optionalCliente.orElseThrow(
				() -> new ClienteNotFoundException(
						"Cliente com id = " + id + " não encontrado!"));
	}
	
	@Transactional
	public Cliente cadastraCliente(Cliente cliente) {
		cliente.setId(null); // garantido da requisicao seja nulo, para nao realizar update
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente atualizaCliente(Cliente atualizaCliente) {
		Cliente cliente = buscaPorId(atualizaCliente.getId());
		atualizaDados(cliente, atualizaCliente);
		return clienteRepository.save(cliente);
	}

	public List<Cliente> buscaTodos() {
		return clienteRepository.findAll();
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteSaveDTO clienteSaveDTO) {
		Cliente cliente = new Cliente(
				null, 
				clienteSaveDTO.getNome(),
				passwordEncoder.encode(clienteSaveDTO.getSenha()),
				clienteSaveDTO.getEmail(), 
				clienteSaveDTO.getCpfOuCnpj(), 
				TipoCliente.toEnum(clienteSaveDTO.getTipoCliente()));
		
		Cidade cidade = new Cidade(clienteSaveDTO.getCidadeId(), null, null);
		
		Endereco endereco = new Endereco(
				null, 
				clienteSaveDTO.getLogradouro(), 
				clienteSaveDTO.getNumero(), 
				clienteSaveDTO.getComplemento(),
				clienteSaveDTO.getBairro(), 
				clienteSaveDTO.getCep(), 
				cliente, 
				cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteSaveDTO.getTelefone());
		
		if(clienteSaveDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteSaveDTO.getTelefone2());
		}
		if(clienteSaveDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteSaveDTO.getTelefone3());
		}
		
		return cliente;
	}
	
	private void atualizaDados(Cliente cliente, Cliente atualizaCliente) {
		cliente.setNome(atualizaCliente.getNome());
		cliente.setEmail(atualizaCliente.getEmail());
	}

	public void deletaCliente(Long id) {
		buscaPorId(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possuir excluir o cliente pois o mesmo possui pedidos!");
		}
	}
}
