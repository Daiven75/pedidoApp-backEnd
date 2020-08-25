package com.lucasilva.pedidoapp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.domain.Cidade;
import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.Endereco;
import com.lucasilva.pedidoapp.domain.Estado;
import com.lucasilva.pedidoapp.domain.ItemPedido;
import com.lucasilva.pedidoapp.domain.Pagamento;
import com.lucasilva.pedidoapp.domain.PagamentoComBoleto;
import com.lucasilva.pedidoapp.domain.PagamentoComCartao;
import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.domain.enums.EstadoPagamento;
import com.lucasilva.pedidoapp.domain.enums.Perfil;
import com.lucasilva.pedidoapp.domain.enums.TipoCliente;
import com.lucasilva.pedidoapp.repositories.CategoriaRepository;
import com.lucasilva.pedidoapp.repositories.CidadeRepository;
import com.lucasilva.pedidoapp.repositories.ClienteRepository;
import com.lucasilva.pedidoapp.repositories.EnderecoRepository;
import com.lucasilva.pedidoapp.repositories.EstadoRepository;
import com.lucasilva.pedidoapp.repositories.ItemPedidoRepository;
import com.lucasilva.pedidoapp.repositories.PagamentoRepository;
import com.lucasilva.pedidoapp.repositories.PedidoRepository;
import com.lucasilva.pedidoapp.repositories.ProdutoRepository;

@Service
public class DBService {

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
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Móveis");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Perfumaria");
		Categoria cat5 = new Categoria(null, "Decoração");
		Categoria cat6 = new Categoria(null, "Jardinagem");
		Categoria cat7 = new Categoria(null, "Eletrônicos");
		
		Produto p1 = new Produto(null, "Notebook", 3550.50);
		Produto p2 = new Produto(null, "Mouse", 120.00);
		Produto p3 = new Produto(null, "Rack", 420.00);
		Produto p4 = new Produto(null, "Vaso", 90.00);
		Produto p5 = new Produto(null, "Perfume Eudora", 110.00);
		Produto p6 = new Produto(null, "Toalha", 15.00);
		Produto p7 = new Produto(null, "Fita decorativa", 10.00);
		Produto p8 = new Produto(null, "Micro-ondas", 230.00);
		Produto p9 = new Produto(null, "Macbook Pro", 9500.00);
		Produto p10 = new Produto(null, "Geladeira", 1400.00);
		Produto p11 = new Produto(null, "Iphone Xr", 3200.00);
		Produto p12 = new Produto(null, "Perfume Natura", 125.00);
		Produto p13 = new Produto(null, "Toalha de rosto", 6.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p9, p11));
		cat2.getProdutos().addAll(Arrays.asList(p3));
		cat3.getProdutos().addAll(Arrays.asList(p6, p13));
		cat4.getProdutos().addAll(Arrays.asList(p5, p12));
		cat5.getProdutos().addAll(Arrays.asList(p7));
		cat6.getProdutos().addAll(Arrays.asList(p4));
		cat7.getProdutos().addAll(Arrays.asList(p8, p10));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat7));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat2));
		p4.getCategorias().addAll(Arrays.asList(cat6));
		p5.getCategorias().addAll(Arrays.asList(cat4));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat5));
		p8.getCategorias().addAll(Arrays.asList(cat7));
		p9.getCategorias().addAll(Arrays.asList(cat1));
		p10.getCategorias().addAll(Arrays.asList(cat7));
		p11.getCategorias().addAll(Arrays.asList(cat1));
		p12.getCategorias().addAll(Arrays.asList(cat4));
		p13.getCategorias().addAll(Arrays.asList(cat3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2 ,p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13));
		
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
				passwordEncoder.encode("senhaQualquer"),
				"75.lucas.slima@gmail.com", 
				"67383494102", 
				TipoCliente.PESSOAFISICA);
		c1.getTelefones().addAll(Arrays.asList("989341021", "9898313410"));
		
		Cliente c2 = new Cliente(
				null, 
				"Priscila", 
				passwordEncoder.encode("senhaQualquer"),
				"75.lucas.slima2@gmail.com", 
				"61734323310", 
				TipoCliente.PESSOAFISICA);
		c2.addPerfil(Perfil.ADMIN);
		c2.getTelefones().addAll(Arrays.asList("98988424043", "98985147336"));
		
		
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
		
		Endereco end3 = new Endereco(
				null, 
				"avenida Jeronimo de Albuquerque", 
				"12", 
				"Potiguar", 
				"Cohama", 
				"650068112", 
				c2, 
				cid4);
		
		c1.getEnderecos().addAll(Arrays.asList(end1, end2));
		c2.getEnderecos().addAll(Arrays.asList(end3));
		
		clienteRepository.saveAll(Arrays.asList(c1, c2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/03/2020 10:32"), c1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("12/04/2020 17:11"), c1, end2);
		Pedido ped3 = new Pedido(null, sdf.parse("18/05/2020 13:29"), c2, end3);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 5);
		ped1.setPagamento(pag1);
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, null, sdf.parse("17/04/2020 00:00"));
		ped2.setPagamento(pag2);
		Pagamento pag3 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped3, 4);
		ped3.setPagamento(pag3);
		
		c1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		c2.getPedidos().addAll(Arrays.asList(ped3));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2, pag3));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 3550.00);
		ItemPedido ip2 = new ItemPedido(ped2, p2, 20.00, 1, 120.00);
		ItemPedido ip3 = new ItemPedido(ped1, p3, 40.00, 2, 420.00);
		ItemPedido ip4 = new ItemPedido(ped3, p9, 0.0, 1, 9500.00);
		ItemPedido ip5 = new ItemPedido(ped3, p11, 0.0, 2, 3200.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip3));
		ped2.getItens().addAll(Arrays.asList(ip2));
		ped3.getItens().addAll(Arrays.asList(ip4, ip5));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip3));
		p9.getItens().addAll(Arrays.asList(ip4));
		p11.getItens().addAll(Arrays.asList(ip5));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3, ip4, ip5));
	}
}
