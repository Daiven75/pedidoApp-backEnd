package com.lucasilva.pedidoapp.services;

import org.springframework.mail.SimpleMailMessage;

import com.lucasilva.pedidoapp.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
