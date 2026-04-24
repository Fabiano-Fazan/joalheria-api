package com.joalheria.api.controller;

import com.joalheria.api.dto.request.ProdutoRequestDTO;
import com.joalheria.api.dto.response.ProdutoResponseDTO;
import com.joalheria.api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/produtos")
public class ProdutosController {
    private final ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutos(Pageable pageable){
        return ResponseEntity.ok(produtoService.listarProdutos(pageable));
    }

    @GetMapping("/nome")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutosPorNome(@RequestParam String name, Pageable pageable){
        return ResponseEntity.ok(produtoService.listarProdutosPorNome(name, pageable));
    }

    @GetMapping("/categoria")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutosPorCategoria(@RequestParam String categoria, Pageable pageable){
        return ResponseEntity.ok(produtoService.listarProdutosPorCategoria(categoria, pageable));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        return ResponseEntity.ok(produtoService.cadastrarProduto(produtoRequestDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable UUID id, @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produtoRequestDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
