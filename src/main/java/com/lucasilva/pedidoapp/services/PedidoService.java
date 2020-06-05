package com.lucasilva.pedidoapp.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasilva.pedidoapp.domain.Cliente;
import com.lucasilva.pedidoapp.domain.ItemPedido;
import com.lucasilva.pedidoapp.domain.PagamentoComBoleto;
import com.lucasilva.pedidoapp.domain.Pedido;
import com.lucasilva.pedidoapp.domain.enums.EstadoPagamento;
import com.lucasilva.pedidoapp.repositories.ItemPedidoRepository;
import com.lucasilva.pedidoapp.repositories.PagamentoRepository;
import com.lucasilva.pedidoapp.repositories.PedidoRepository;
import com.lucasilva.pedidoapp.security.UserSS;
import com.lucasilva.pedidoapp.services.exceptions.AuthorizationException;
import com.lucasilva.pedidoapp.services.exceptions.PedidoNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscaPorId(Long id) {
		Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
		return optionalPedido.orElseThrow(
				() -> new PedidoNotFoundException(
						"Pedido de id = " + id + " não encontrado!"));
	}

	@Transactional
	public Pedido cadastraPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.buscaPorId(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagtoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagtoComBoleto, pedido.getInstante());
		}
		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.buscaPorId(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}
	
	public Page<Pedido> buscaPagina(Integer pagina, Integer linhasPorPagina, String ordenaPor, String direcao) {
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("acesso negado!");
		}
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenaPor);

		Cliente cliente = clienteService.buscaPorId(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
