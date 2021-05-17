package com.lucasilva.pedidoapp.services;

import com.lucasilva.pedidoapp.domain.enums.TipoErro;
import com.lucasilva.pedidoapp.repositories.ClienteRepository;
import com.lucasilva.pedidoapp.services.exceptions.ClienteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private final Random random = new Random();

	@Autowired
	private EmailService emailService;
	
	public void sendNewPassword(String email) {
		
		var cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ClienteNotFoundException(TipoErro.EMAIL_NAO_ENCONTRADO.toString());
		}
		
		String newPassword = newPassword();
		cliente.setSenha(passwordEncoder.encode(newPassword));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		var vet = new char[10];
		for(var i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		var op = random.nextInt();
		if(op == 0) {
			return (char) (random.nextInt(10) + 48); // gera um digito
		}
		else if(op == 1) {
			return (char) (random.nextInt(26) + 65); // gera letra maiscula
		} 
		else {
			return (char) (random.nextInt(26) + 97); // gera letra minuscula
		}
	}
}