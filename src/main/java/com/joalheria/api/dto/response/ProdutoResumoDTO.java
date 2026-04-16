package com.joalheria.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResumoDTO(
        UUID id,
        String nome,
        BigDecimal preco,
        String fotoUrl
) {}
