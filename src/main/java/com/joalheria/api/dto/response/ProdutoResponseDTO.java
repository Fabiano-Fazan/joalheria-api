package com.joalheria.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String fotoUrl,
        String cor,
        String categoria,
        Integer quantidade,
        Boolean disponivel
) {}
