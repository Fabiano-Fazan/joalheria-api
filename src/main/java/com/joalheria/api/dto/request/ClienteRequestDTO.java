package com.joalheria.api.dto.request;


import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO (

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O endereço é obrigatório")
        String endereco
){}

