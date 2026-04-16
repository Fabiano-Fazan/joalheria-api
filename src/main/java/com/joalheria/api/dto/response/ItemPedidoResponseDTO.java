package com.joalheria.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponseDTO(
        UUID id,
        ProdutoResumoDTO produto,
        Integer quantidade,
        BigDecimal total

) {}
