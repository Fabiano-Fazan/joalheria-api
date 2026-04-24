package com.joalheria.api.controller;

import com.joalheria.api.dto.request.EstoqueRequestDTO;
import com.joalheria.api.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;

    @PostMapping("/entrada/{id}")
    public ResponseEntity<Void> entradaEstoque(@PathVariable UUID id,@Valid @RequestBody EstoqueRequestDTO estoqueRequestDTO){
        estoqueService.entradaEstoque(id, estoqueRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saida/{id}")
    public ResponseEntity<Void> saidaEstoque(@PathVariable UUID id, @Valid @RequestBody EstoqueRequestDTO estoqueRequestDTO){
        estoqueService.saidaEstoque(id, estoqueRequestDTO);
        return ResponseEntity.ok().build();
    }
}
