package com.joalheria.api.dto.response;

import com.joalheria.api.model.entity.ProdutoImagem;

import java.util.UUID;

public record ProdutoImagemResponseDTO (
        UUID id,
        String imagemUrl,
        Boolean imagemPrincipal
    )
{
    public ProdutoImagemResponseDTO(ProdutoImagem produtoImagem) {
        this(
                produtoImagem.getId(),
                produtoImagem.getImagemUrl(),
                produtoImagem.getImagemPrincipal()
        );
    }
}