package com.joalheria.api.repositoy;

import com.joalheria.api.dto.response.DashboardResponseDTO;
import com.joalheria.api.dto.response.ProdutoMaisVendidoDTO;
import com.joalheria.api.model.entity.Pedido;
import com.joalheria.api.model.enums.PedidoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    Page<Pedido> findByClienteEmailIgnoreCase(String email, Pageable pageable);


    @Query("""
     select new com.joalheria.api.dto.response.DashboardResponseDTO(
        :inicio,
        :fim,
        COUNT(p)
     )
     FROM Pedido p
     WHERE p.status = :completo
     AND p.dataPedido BETWEEN :inicio AND :fim
     """)
    DashboardResponseDTO contarTotalPedidoCompletoEntreDatas(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("completo") PedidoStatus completo
            );

    @Query("""
     SELECT COALESCE(SUM(p.valorTotal), 0)
     FROM Pedido p
     WHERE p.status = :completo
     AND p.dataPedido BETWEEN :inicio AND :fim
     """)
    BigDecimal calcularValorTotalEntreDatas(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("completo") PedidoStatus completo
            );

    @Query("""
    select new com.joalheria.api.dto.response.ProdutoMaisVendidoDTO(
        ip.produto.nome,
        sum(ip.quantidade)
    )
    FROM ItemPedido ip
    WHERE ip.pedido.status = :completo
    AND ip.pedido.dataPedido between :inicio and :fim
    GROUP BY ip.produto.nome
    ORDER BY sum(ip.quantidade) desc
""")
    List<ProdutoMaisVendidoDTO> buscarProdutosMaisVendidosEntreDatas(
           @Param("inicio") LocalDateTime inicio,
           @Param("fim") LocalDateTime fim,
            @Param("completo") PedidoStatus completo,
            Pageable pageable
    );
}
