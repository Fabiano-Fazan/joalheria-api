package com.joalheria.api.dto.response;

import com.joalheria.api.model.entity.Cliente;

import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String email,
        String telefone,
        String endereco,
        String dataCadastro
) {
    public ClienteResponseDTO(Cliente cliente){
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getDataCadastro().toString()
        );
    }
}
