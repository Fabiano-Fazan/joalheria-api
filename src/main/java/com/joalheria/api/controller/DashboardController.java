package com.joalheria.api.controller;

import com.joalheria.api.dto.request.DashBoardRequestDTO;
import com.joalheria.api.dto.response.DashboardResponseDTO;
import com.joalheria.api.dto.response.ProdutoMaisVendidoDTO;
import com.joalheria.api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashbord")
public class DashboardController {
    private final DashboardService dashboardService;


    @GetMapping("/pedidos-mes")
    public ResponseEntity<DashboardResponseDTO> filtraPedidosMes(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        DashBoardRequestDTO requestDTO = new DashBoardRequestDTO(inicio.atStartOfDay(), fim.atTime(23, 59, 59));
        DashboardResponseDTO responseDTO = dashboardService.filtraPedidosMes(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/valor-total-mes")
    public ResponseEntity<BigDecimal> valorTotalVendidoMesAtual(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        BigDecimal valorTotal = dashboardService.valorTotalVendidoEntreDatas(
                inicio.atStartOfDay(),
                fim.atTime(23, 59, 59)
        );
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("produto-mais-vendido-mes")
    public ResponseEntity<List<ProdutoMaisVendidoDTO>> produtosMaisVendidosMesAtual(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(dashboardService.produtosMaisVendidosEntreDatas(
                inicio.atStartOfDay(),
                fim.atTime(23, 59, 59),
                pageable
        ));
    }
}
