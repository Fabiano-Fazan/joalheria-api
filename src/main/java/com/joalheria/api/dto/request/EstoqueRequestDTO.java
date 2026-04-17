package com.joalheria.api.dto.request;

import com.joalheria.api.model.enums.EstoqueTipo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EstoqueRequestDTO(

        @NotNull(message = "A quantidade é obrigatória" )
        @Positive(message = "A quantidade deve ser um número positivo")
        Integer quantidade,

        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço deve ser um número positivo")
        BigDecimal preco,

        @NotNull(message = "O tipo é obrigatório")
        EstoqueTipo tipo
) {}
