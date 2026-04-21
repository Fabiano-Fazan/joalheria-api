package com.joalheria.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id ;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private String fotoUrl;

    private String cor;

    private String categoria;

    private Integer quantidade = 0;

    private Boolean disponivel;

    @PrePersist
    public void quantidadePadrao() {
        if (quantidade == null) {
            quantidade = 0;
        }
    }
}
