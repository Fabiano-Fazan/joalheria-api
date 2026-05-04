package com.joalheria.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Integer quantidade = 0;

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(nullable = false)
    private Boolean destaque = false;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoImagem> imagens = new ArrayList<>();

    @PrePersist
    public void quantidadePadrao() {
        if (quantidade == null) {
            quantidade = 0;
        }
    }
}
