package com.lucasilva.pedidoapp;

import java.text.SimpleDateFormat;
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
import com.lucasilva.pedidoapp.domain.ItemPedido;
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
import com.lucasilva.pedidoapp.repositories.ItemPedidoRepository;
import com.lucasilva.pedidoapp.repositories.PagamentoRepository;
import com.lucasilva.pedidoapp.repositories.PedidoRepository;
import com.lucasilva.pedidoapp.repositories.ProdutoRepository;

@SpringBootApplication
public class PedidoappApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PedidoappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}