package com.joalheria.api.dto.response;

import com.joalheria.api.model.entity.ItemPedido;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponseDTO(
        UUID id,
        String nomeProduto,
        Integer quantidade,
        BigDecimal preco,
        String fotoUrl

) {
    public ItemPedidoResponseDTO(ItemPedido itemPedido) {
        this(
                itemPedido.getId(),
                itemPedido.getProduto().getNome(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco(),
                itemPedido.getProduto().getFotoUrl()
        );
    }
}
