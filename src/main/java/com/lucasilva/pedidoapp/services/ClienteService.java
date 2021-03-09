package com.lucasilva.pedidoapp.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.cliente.profile}")
	private String prefix;
	
	@Value("${img.profiles.size}")
	private Integer size;
	
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

	public Cliente atualizaCliente(Long id, ClienteDTO clienteDTO) {
		Cliente cliente = buscaPorId(id);
		atualizaDados(cliente, clienteDTO);
		return clienteRepository.save(cliente);
	}

	public List<Cliente> buscaTodos() {
		return clienteRepository.findAll();
	}
	
	public Cliente buscaPorEmail(String email) {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) || !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado."); 
		}
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ClienteNotFoundException(
					"Cliente não encontrado! "
					+ "Id: " + user.getId() + " , Tipo: " + Cliente.class.getName());
		}
		
		return cliente;
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
	
	private void atualizaDados(Cliente cliente, ClienteDTO atualizaCliente) {
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
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		BufferedImage img = imageService.getJpgImageFromFile(multipartFile);
		img = imageService.cropSquare(img);
		img = imageService.resize(img, size);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(fileName, imageService.getInputStream(img, "jpg"), "image");
	}
}
