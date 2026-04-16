package com.joalheria.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        LocalDateTime dataPedido,
        String observacoes,
        String  linkWhatsapp,
        BigDecimal valorTotal,
        String status,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {}
