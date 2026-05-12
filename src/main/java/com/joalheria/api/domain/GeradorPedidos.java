package com.joalheria.api.domain;

import com.joalheria.api.dto.request.ItemPedidoResquestDTO;
import com.joalheria.api.exception.RecursoNaoEncontradoException;
import com.joalheria.api.model.entity.ItemPedido;
import com.joalheria.api.model.entity.Pedido;
import com.joalheria.api.model.entity.Produtos;
import com.joalheria.api.model.enums.EstoqueTipo;
import com.joalheria.api.repositoy.ProdutoRespository;
import com.joalheria.api.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class GeradorPedidos {

    private final ProdutoRespository produtoRespository;
    private final EstoqueService estoqueService;

    public ItemPedido criarItemPedido(ItemPedidoResquestDTO dto, Pedido pedido){
        Produtos produto = produtoRespository.findWithLockById(dto.produtoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        return ItemPedido.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(dto.quantidade())
                .preco(produto.getPreco())
                .build();
    }

    public BigDecimal calcularValorTotal(Pedido pedido){
        return pedido.getItens().stream()
                .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void registraMovimentoEstoque(Pedido pedido){
        pedido.getItens().forEach(item -> estoqueService.saidaEstoque(
                item.getProduto(),
                item.getQuantidade(),
                item.getPreco(),
                EstoqueTipo.SAIDA,
                pedido
        ));

    }

    public void reverterMovimentoEstoque(Pedido pedido){
        pedido.getItens().forEach(item -> estoqueService.entradaEstoque(
                item.getProduto(),
                item.getQuantidade(),
                item.getPreco(),
                EstoqueTipo.ENTRADA,
                pedido
        ));
    }
}
