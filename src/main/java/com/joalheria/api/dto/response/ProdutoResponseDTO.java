package com.joalheria.api.dto.response;

import com.joalheria.api.model.entity.Produtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProdutoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String cor,
        String categoria,
        Integer quantidade,
        Boolean inativo,
        Boolean destaque,
        List<ProdutoImagemResponseDTO> imagens

) {
    public ProdutoResponseDTO(Produtos produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCor(),
                produto.getCategoria(),
                produto.getQuantidade(),
                produto.getInativo(),
                produto.getDestaque(),
                produto.getImagens()
                        .stream()
                        .map(ProdutoImagemResponseDTO::new)
                        .toList()
        );
    }
}
