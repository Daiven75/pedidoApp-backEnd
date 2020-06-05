package com.lucasilva.pedidoapp.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.repositories.ClienteRepository;
import com.lucasilva.pedidoapp.services.exceptions.ClienteNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private Random random = new Random();
	
	@Autowired
	private EmailService emailService;
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ClienteNotFoundException("Email não encontrado!");
		}
		
		String newPassword = newPassword();
		cliente.setSenha(passwordEncoder.encode(newPassword));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int op = random.nextInt();
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
