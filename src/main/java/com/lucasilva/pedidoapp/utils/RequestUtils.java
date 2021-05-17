package com.lucasilva.pedidoapp.utils;

import com.lucasilva.pedidoapp.domain.CepDTO;
import com.lucasilva.pedidoapp.domain.Endereco;
import com.lucasilva.pedidoapp.domain.enums.TipoErro;
import com.lucasilva.pedidoapp.services.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
public class RequestUtils {

    public Endereco getRequestCep(String cep) {
        try {
            var url = "https://viacep.com.br/ws/" + cep + "/json/";
            return new Endereco(WebClient.create(url)
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(CepDTO.class)
                    .block());
        } catch (WebClientException e) {
            throw new BadRequestException(TipoErro.ERRO_CONSULTAR_CEP.toString());
        }
    }
}