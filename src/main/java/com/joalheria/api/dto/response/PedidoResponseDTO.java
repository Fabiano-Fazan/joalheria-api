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
        String linkWhatsapp,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {
    public PedidoResponseDTO(Pedido pedido){
        this(pedido,null);
    }


    public PedidoResponseDTO(Pedido pedido,String linkWhatsapp) {
        this(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getObservacoes(),
                pedido.getValorTotal(),
                pedido.getStatus() != null ? pedido.getStatus().name() : null,
                linkWhatsapp,
                new ClienteResponseDTO(pedido.getCliente()),
                pedido.getItens().stream().map(ItemPedidoResponseDTO::new).toList()
        );
    }
}
