package com.lucasilva.pedidoapp.config;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.lucasilva.pedidoapp.domain.enums.Perfil;
import com.lucasilva.pedidoapp.security.UserSS;
import com.lucasilva.pedidoapp.services.DBService;
import com.lucasilva.pedidoapp.services.EmailService;
import com.lucasilva.pedidoapp.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	private DBService dbService;

	public TestConfig(DBService dbService) {
		this.dbService = dbService;
	}
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		Set<Perfil> perfis = new HashSet<Perfil>(Arrays.asList(Perfil.ADMIN));
		UserSS userActive = new UserSS(1L, "75.lucas.slima@gmail.com", "4321", perfis);
		
		return new InMemoryUserDetailsManager(userActive);
	}
}
