package com.joalheria.api.model.enums;

public enum PedidoStatus {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    PedidoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
