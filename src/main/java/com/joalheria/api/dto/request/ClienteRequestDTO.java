package com.joalheria.api.dto.request;

import com.joalheria.api.model.entity.Cliente;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO (

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O endereço é obrigatório")
        String endereco
){
        public Cliente toEntity() {
                return Cliente.builder()
                        .telefone(telefone)
                        .endereco(endereco)
                        .build();
        }
}

