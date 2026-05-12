package com.joalheria.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResquestDTO(

        @NotNull(message = "O ID do produto é obrigatório")
        UUID produtoId,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        String produtoNome,

        BigDecimal preco
) {}
