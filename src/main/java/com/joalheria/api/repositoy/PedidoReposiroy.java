package com.joalheria.api.repositoy;

import com.joalheria.api.dto.response.ProdutoMaisVendidoDTO;
import com.joalheria.api.model.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PedidoReposiroy extends JpaRepository<Pedido, UUID> {
    Page<Pedido> findByClienteEmailContainingIgnoreCase(String nome, Pageable pageable);

    Long countByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT SUM(p.valorTotal) FROM Pedido p WHERE p.dataPedido BETWEEN :inicio AND :fim")
    BigDecimal calcularValorTotalEntreDatas(LocalDateTime inicio, LocalDateTime fim);

    @Query("""
    select new com.joalheria.api.dto.response.ProdutoMaisVendidoDTO(
        ip.produto.nome,
        sum(ip.quantidade)
    )
    from ItemPedido ip
    where ip.pedido.dataPedido between :inicio and :fim
    group by ip.produto.nome
    order by sum(ip.quantidade) desc
""")
    List<ProdutoMaisVendidoDTO> buscarProdutosMaisVendidosMesAtual(
           @Param("inicio") LocalDateTime inicio,
           @Param("fim") LocalDateTime fim,
            Pageable pageable
    );
}
