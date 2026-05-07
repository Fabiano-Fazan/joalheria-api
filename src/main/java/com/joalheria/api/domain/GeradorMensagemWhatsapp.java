package com.joalheria.api.domain;

import com.joalheria.api.model.entity.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GeradorMensagemWhatsapp {

    @Value("${whatsapp.numero}")
    private String numeroWhatsapp;

    public String gerarLinkWhatsapp(Pedido pedido){

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Olá! Segue o meu pedido:\n\n");
        pedido.getItens().forEach(itemPedido -> mensagem.append("- ")
                .append(itemPedido.getProduto().getNome())
                .append(" | Qtd: ")
                .append(itemPedido.getQuantidade())
                .append(" | R$ ")
                .append(itemPedido.getPreco())
                .append("\n"));

        mensagem.append("\nValor Total: R$ ")
                .append(pedido.getValorTotal());

        if(pedido.getObservacoes() != null && !pedido.getObservacoes().isBlank()){
            mensagem.append("\n\nObservações: ")
                    .append(pedido.getObservacoes());
        }

        String mensagemCodificada = URLEncoder.encode(
                mensagem.toString(),
                StandardCharsets.UTF_8
        );

        return "https://wa.me/"
                + numeroWhatsapp
                + "?text="
                + mensagemCodificada;
    }
}
