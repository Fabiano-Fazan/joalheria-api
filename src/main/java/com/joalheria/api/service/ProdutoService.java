package com.joalheria.api.service;

import com.joalheria.api.dto.request.ProdutoRequestDTO;
import com.joalheria.api.dto.response.ProdutoResponseDTO;
import com.joalheria.api.model.entity.Produtos;
import com.joalheria.api.repositoy.ProdutoRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRespository produtoRespository;

    public Page<ProdutoResponseDTO> listarProdutos(Pageable pageable){
        return produtoRespository.findAll(pageable)
                .map(ProdutoResponseDTO::new);
    }

    public Page<ProdutoResponseDTO> listarProdutosPorNome(String nome, Pageable pageable){
        return produtoRespository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(ProdutoResponseDTO::new);
    }

    public Page<ProdutoResponseDTO> listarProdutosPorCategoria(String categoria, Pageable pageable){
        return produtoRespository.findByCategoriaContainingIgnoreCase(categoria, pageable)
                .map(ProdutoResponseDTO::new);
    }

    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO){
        Produtos produto = new Produtos();
        produtoRespository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(UUID id, ProdutoRequestDTO produtoRequestDTO){
        Produtos produto = produtoRespository.findWithLockById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setNome(produtoRequestDTO.nome());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setPreco(produtoRequestDTO.preco());
        produto.setCor(produtoRequestDTO.cor());
        produto.setCategoria(produtoRequestDTO.categoria());
        produto.setQuantidade(produtoRequestDTO.quantidade());
        return new ProdutoResponseDTO(produto);
        }

    @Transactional
    public void deletarProduto(UUID id){
        produtoRespository.deleteById(id);

    }
}
