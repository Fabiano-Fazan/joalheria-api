package com.joalheria.api.service;

import com.joalheria.api.dto.response.ProdutoMaisVendidoDTO;
import com.joalheria.api.repositoy.PedidoReposiroy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashbordService {

    private final PedidoReposiroy pedidoReposiroy;

    public Long filtraPedidosMesAtual() {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fim = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        return pedidoReposiroy.countByDataPedidoBetween(inicio, fim);
    }

    public BigDecimal valorTotalVendidoMesAtual() {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fim = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        BigDecimal total = pedidoReposiroy.calcularValorTotalEntreDatas(inicio, fim);
        return total == null ? BigDecimal.ZERO : total;
    }

    public List<ProdutoMaisVendidoDTO> produtosMaisVendidosMesAtual(Pageable pageable) {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fim = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        return pedidoReposiroy.buscarProdutosMaisVendidosMesAtual(inicio, fim, pageable);
    }

}
