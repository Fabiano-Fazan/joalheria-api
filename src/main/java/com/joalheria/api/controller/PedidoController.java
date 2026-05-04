package com.joalheria.api.controller;

import com.joalheria.api.dto.request.PedidoRequestDTO;
import com.joalheria.api.dto.response.PedidoResponseDTO;
import com.joalheria.api.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping("/pedidos")
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(Pageable pageable){
        return ResponseEntity.ok(pedidoService.listarPedidos(pageable));
    }

    @GetMapping("Cliente")
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidosPorCliente(@Valid @RequestParam String nome, Pageable pageable){
        return ResponseEntity.ok(pedidoService.listarPorCliente(nome, pageable));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO, String emailCliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(pedidoRequestDTO,emailCliente));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable String id) {
        pedidoService.deletarPedido(java.util.UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
