package com.joalheria.api.service;

import com.joalheria.api.dto.request.EstoqueRequestDTO;
import com.joalheria.api.exception.RecursoNaoEncontradoException;
import com.joalheria.api.model.entity.Estoque;
import com.joalheria.api.model.entity.Pedido;
import com.joalheria.api.model.entity.Produtos;
import com.joalheria.api.model.enums.EstoqueTipo;
import com.joalheria.api.repositoy.EstoqueRepository;
import com.joalheria.api.repositoy.ProdutoRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRespository produtoRespository;

    @Transactional
    public void entradaEstoque(Produtos produtos, Integer quantidade, BigDecimal preco, EstoqueTipo tipo, Pedido pedido){
        Produtos produto = produtoRespository.findWithLockById(produtos.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        produto.setQuantidade(produto.getQuantidade() + quantidade);
        produtoRespository.save(produto);
        Estoque estoque = Estoque.builder()
                .produto(produtos)
                .quantidade(quantidade)
                .preco(preco)
                .tipo(tipo)
                .pedido(pedido)
                .build();
        estoqueRepository.save(estoque);

    }

    @Transactional
    public void entradaEstoque(UUID produtoId, EstoqueRequestDTO estoqueRequestDTO){
        Produtos produto = produtoRespository.findWithLockById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        this.entradaEstoque(produto, estoqueRequestDTO.quantidade(), estoqueRequestDTO.preco(),estoqueRequestDTO.tipo(), null);
    }

    @Transactional
    public void saidaEstoque(Produtos produtos, Integer quantidade, BigDecimal preco, EstoqueTipo tipo, Pedido pedido){
        Produtos produto = produtoRespository.findWithLockById(produtos.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        if (produto.getQuantidade() < quantidade) {
            throw new RuntimeException("Quantidade insuficiente em estoque");
        }
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoRespository.save(produto);
        Estoque estoque = Estoque.builder()
                .produto(produtos)
                .quantidade(quantidade)
                .preco(preco)
                .tipo(tipo)
                .pedido(pedido)
                .build();
        estoqueRepository.save(estoque);
    }

    @Transactional
    public void saidaEstoque(UUID produtoId, EstoqueRequestDTO estoqueRequestDTO){
        Produtos produto = produtoRespository.findWithLockById(produtoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        this.saidaEstoque(produto, estoqueRequestDTO.quantidade(), estoqueRequestDTO.preco(),estoqueRequestDTO.tipo(), null);
    }
}
