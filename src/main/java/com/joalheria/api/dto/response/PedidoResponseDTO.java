package com.joalheria.api.dto.response;

import com.joalheria.api.model.entity.Pedido;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        LocalDateTime dataPedido,
        String observacoes,
        BigDecimal valorTotal,
        String status,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {
    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getObservacoes(),
                pedido.getValorTotal(),
                pedido.getStatus().toString(),
                new ClienteResponseDTO(pedido.getCliente()),
                pedido.getItens().stream().map(ItemPedidoResponseDTO::new).toList()
        );
    }
}
