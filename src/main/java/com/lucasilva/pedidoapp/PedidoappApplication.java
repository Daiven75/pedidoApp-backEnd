package com.lucasilva.pedidoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasilva.pedidoapp.services.S3Service;

@SpringBootApplication
public class PedidoappApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(PedidoappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\temp\\parque.jpg");
	}
}