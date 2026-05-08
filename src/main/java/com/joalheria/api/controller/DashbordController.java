package com.joalheria.api.controller;

import com.joalheria.api.service.DashbordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashbord")
public class DashbordController {
    private final DashbordService dashbordService;


    @GetMapping("/pedidos-mes-atual")
    public ResponseEntity<Long> filtraPedidosMesAtual() {
        Long totalPedidos = dashbordService.filtraPedidosMesAtual();
        return ResponseEntity.ok(totalPedidos);
    }

    @GetMapping("/valor-total-mes-atual")
    public ResponseEntity<BigDecimal> valorTotalVendidoMesAtual() {
        BigDecimal valorTotal = dashbordService.valorTotalVendidoMesAtual();
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("produto-mais-vendido")
    public ResponseEntity<List> produtosMaisVendidosMesAtual(
            @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(dashbordService.produtosMaisVendidosMesAtual(pageable));
    }
}
