package com.joalheria.api.domain;

import com.joalheria.api.model.entity.Pedido;
import org.springframework.stereotype.Component;

@Component
public class GeradorMensagemWhatsapp {

    public String geradorMensagem(Pedido pedido){

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Olá! Segue o meu pedido:%n%n");
        pedido.getItens().forEach(itemPedido ->{
            mensagem.append("- ")
                    .append(itemPedido.getProduto().getNome())
                    .append(" | Qtd: ")
                    .append(itemPedido.getQuantidade())
                    .append(" | R$ ")
                    .append(itemPedido.getPreco())
                    .append("%n");
        } );
        mensagem.append("%nValor Total: R$ ")
                .append(pedido.getValorTotal());
        return String.format(mensagem.toString());
    }
}
