package com.joalheria.api.service;

import com.joalheria.api.dto.request.ProdutoRequestDTO;
import com.joalheria.api.dto.response.ProdutoResponseDTO;
import com.joalheria.api.exception.NegocioException;
import com.joalheria.api.model.entity.ProdutoImagem;
import com.joalheria.api.model.entity.Produtos;
import com.joalheria.api.repositoy.ProdutoRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRespository produtoRespository;
    private final CloudinaryService cloudinaryService;

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

    public List<ProdutoResponseDTO> listarProdutosDestaque(){
        return produtoRespository.findByDestaqueTrue()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO, List<MultipartFile> imagens, Integer imagemPrincipalIndex){
        validaImagens(imagens, imagemPrincipalIndex);
        Produtos produto = new Produtos();
        atualizaDados(produtoRequestDTO, produto);
        produto.setDisponivel(true);

        for (int i = 0; i < imagens.size(); i++) {

            String url = cloudinaryService.uploadProdutoImagem(imagens.get(i), produto.getId());

            ProdutoImagem img = ProdutoImagem.builder()
                    .imagemUrl(url)
                    .imagemPrincipal(i == imagemPrincipalIndex)
                    .produto(produto)
                    .build();

            produto.getImagens().add(img);
        }
        produtoRespository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(UUID id, ProdutoRequestDTO produtoRequestDTO){
        Produtos produto = produtoRespository.findWithLockById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        atualizaDados(produtoRequestDTO, produto);
        return new ProdutoResponseDTO(produto);
        }


    @Transactional
    public void deletarProduto(UUID id){
        produtoRespository.deleteById(id);

    }

    private void atualizaDados(ProdutoRequestDTO produtoRequestDTO, Produtos produto){
        produto.setNome(produtoRequestDTO.nome());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setPreco(produtoRequestDTO.preco());
        produto.setCor(produtoRequestDTO.cor());
        produto.setCategoria(produtoRequestDTO.categoria());
        produto.setQuantidade(produtoRequestDTO.quantidade());
        produto.setDestaque(Boolean.TRUE.equals(produtoRequestDTO.destaque()));
        produtoRespository.save(produto);
    }

    private void validaImagens(List<MultipartFile> imagens, Integer imagemPrincipalIndex){

        if (imagens == null || imagens.isEmpty()) {
            throw new NegocioException("O produto precisa ter pelo menos uma imagem.");
        }

        if (imagens.size() > 4) {
            throw new NegocioException("O produto pode ter no máximo 4 imagens.");
        }

        if (imagemPrincipalIndex == null) {
            throw new NegocioException("Selecione uma imagem principal.");
        }

        if (imagemPrincipalIndex < 0 || imagemPrincipalIndex >= imagens.size()) {
            throw new NegocioException("Imagem principal inválida.");
        }
    }
}
