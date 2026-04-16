package com.joalheria.api.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestDTO(

        @NotEmpty(message = "O pedido precisa ter pelo menos um item")
        List<ItemPedidoResquestDTO> itens,

        String status,

        String observacoes
) {}
