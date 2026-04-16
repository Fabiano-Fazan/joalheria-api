package com.joalheria.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Setter;

import java.math.BigDecimal;

public record ProdutoRequestDTO (

        @NotBlank(message = "O nome do produto é obrigatório")
        String nome,

        @NotBlank(message = "A descrição do produto é obrigatória")
        @Size(max = 500, message = "A descrição do produto deve conter no máximo 500 caracteres")
        String descricao,

        @NotBlank(message = "A cor do produto é obrigatória")
        String cor,

        @NotBlank(message = "A categoria do produto é obrigatória")
        String categoria,

        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço do produto deve ser um valor positivo")
        BigDecimal preco,

        @Min(value = 0, message = "A quantidade do produto deve ser maior ou igual a zero")
        Integer quantidade
){}
