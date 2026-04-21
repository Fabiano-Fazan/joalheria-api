package com.joalheria.api.model.entity;

import com.joalheria.api.model.enums.EstoqueTipo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "estoque_movimento")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produtos produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dataMovimentacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstoqueTipo tipo;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
