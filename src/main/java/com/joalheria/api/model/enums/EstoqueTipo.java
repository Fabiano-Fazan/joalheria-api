package com.joalheria.api.model.enums;

import lombok.Getter;

@Getter
public enum EstoqueTipo {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private final String descricao;

    EstoqueTipo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
