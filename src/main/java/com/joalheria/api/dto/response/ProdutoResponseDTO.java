package com.joalheria.api.dto.response;

import com.joalheria.api.model.entity.Produtos;

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
) {
    public ProdutoResponseDTO(Produtos produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getFotoUrl(),
                produto.getCor(),
                produto.getCategoria(),
                produto.getQuantidade(),
                produto.getDisponivel()
        );
    }
}
