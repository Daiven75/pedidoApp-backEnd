package com.lucasilva.pedidoapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.Endereco;
import com.lucasilva.pedidoapp.domain.Estado;
import com.lucasilva.pedidoapp.domain.Pagamento;
import com.lucasilva.pedidoapp.domain.PagamentoComBoleto;
import com.lucasilva.pedidoapp.domain.PagamentoComCartao;
import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.domain.enums.EstadoPagamento;
import com.lucasilva.pedidoapp.domain.enums.TipoCliente;
import com.lucasilva.pedidoapp.repositories.CategoriaRepository;
import com.lucasilva.pedidoapp.repositories.CidadeRepository;
import com.lucasilva.pedidoapp.repositories.ClienteRepository;
import com.lucasilva.pedidoapp.repositories.EnderecoRepository;
import com.lucasilva.pedidoapp.repositories.EstadoRepository;
import com.lucasilva.pedidoapp.repositories.PagamentoRepository;
import com.lucasilva.pedidoapp.repositories.PedidoRepository;
import com.lucasilva.pedidoapp.repositories.ProdutoRepository;

@SpringBootApplication
public class PedidoappApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PedidoappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Móveis");
		
		Produto p1 = new Produto(null, "Notebook", 3550.50);
		Produto p2 = new Produto(null, "Mouse", 120.00);
		Produto p3 = new Produto(null, "Rack", 420.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2));
		cat2.getProdutos().addAll(Arrays.asList(p3));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2 ,p3));
		
		Estado est1 = new Estado(null, "Maranhão");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Imperatriz", est1);
		Cidade cid2 = new Cidade(null, "Alcântara", est1);
		Cidade cid3 = new Cidade(null, "Tutoia", est1);
		Cidade cid4 = new Cidade(null, "Santos", est2);
		Cidade cid5 = new Cidade(null, "São Bernardo do Rio Preto", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1, cid2, cid3));
		est2.getCidades().addAll(Arrays.asList(cid4, cid5));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3, cid4, cid5));
		
		Cliente c1 = new Cliente(
				null, 
				"Lucas", 
				"lucas@lucas.con", 
				"67383494102", 
				TipoCliente.PESSOAFISICA);
		
		Cliente c2 = new Cliente(
				null, 
				"Priscila", 
				"priscila@priscila.con", 
				"61734323310", 
				TipoCliente.PESSOAFISICA);
		
		c1.getTelefones().addAll(Arrays.asList("989341021", "9898313410"));
		
		Endereco end1 = new Endereco(
				null, 
				"avenida sao vicente", 
				"82", 
				"Center TemTudo", 
				"Santa rosa", 
				"65054312", 
				c1, 
				cid1);
		Endereco end2 = new Endereco(
				null, 
				"avenida das flores", 
				"40", 
				"Shopping Center", 
				"Sol e mar", 
				"65052312", 
				c1, 
				cid1);
		
		c1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(c1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/03/2020 10:32"), c1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("12/04/2020 17:11"), c1, end2);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 5);
		ped1.setPagamento(pag1);
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, null, sdf.parse("17/04/2020 00:00"));
		ped2.setPagamento(pag2);
		
		c1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
	}
}