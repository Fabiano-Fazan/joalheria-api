package com.joalheria.api.service;

import com.joalheria.api.dto.request.DashBoardRequestDTO;
import com.joalheria.api.dto.response.DashboardResponseDTO;
import com.joalheria.api.dto.response.ProdutoMaisVendidoDTO;
import com.joalheria.api.model.enums.PedidoStatus;
import com.joalheria.api.repositoy.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PedidoRepository pedidoRepository;

    public DashboardResponseDTO filtraPedidosMes(DashBoardRequestDTO dashBoardRequestDTO) {
        LocalDateTime inicio = dashBoardRequestDTO.dataInicio();
        LocalDateTime fim = dashBoardRequestDTO.dataFim();
        return pedidoRepository.contarTotalPedidoCompletoEntreDatas(inicio, fim, PedidoStatus.COMPLETO);
    }

    public BigDecimal valorTotalVendidoEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        return pedidoRepository.calcularValorTotalEntreDatas(inicio, fim, PedidoStatus.COMPLETO);
    }

    public List<ProdutoMaisVendidoDTO> produtosMaisVendidosEntreDatas(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return pedidoRepository.buscarProdutosMaisVendidosEntreDatas(inicio, fim, PedidoStatus.COMPLETO, pageable);
    }

}
