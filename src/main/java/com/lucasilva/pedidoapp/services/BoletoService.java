package com.lucasilva.pedidoapp.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagtoComBoleto, Date instanteDoPedido) {
		Calendar car = Calendar.getInstance();
		car.setTime(instanteDoPedido);
		car.add(Calendar.DAY_OF_MONTH, 7);
		pagtoComBoleto.setDataVencimento(car.getTime());
	}
}
