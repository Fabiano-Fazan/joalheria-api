package com.joalheria.api.dto.response;



public record ProdutoMaisVendidoDTO(
        String nomeProduto,
        Long TotalVendido
) {
}
