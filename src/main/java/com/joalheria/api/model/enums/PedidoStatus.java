package com.joalheria.api.model.enums;

import lombok.Getter;

@Getter
public enum PedidoStatus {
    COMPLETO("Completo"),
    CANCELADO("Cancelado");

    private final String descricao;

    PedidoStatus(String descricao) {
        this.descricao = descricao;
    }
}
