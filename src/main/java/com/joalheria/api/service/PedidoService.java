package com.joalheria.api.service;

import com.joalheria.api.domain.GeradorMensagemWhatsapp;
import com.joalheria.api.domain.GeradorPedidos;

import com.joalheria.api.dto.request.PedidoRequestDTO;
import com.joalheria.api.dto.response.PedidoResponseDTO;
import com.joalheria.api.exception.RecursoNaoEncontradoException;
import com.joalheria.api.model.entity.Pedido;
import com.joalheria.api.model.enums.PedidoStatus;
import com.joalheria.api.repositoy.ClienteRepository;
import com.joalheria.api.repositoy.PedidoReposiroy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoReposiroy pedidoReposiroy;
    private final GeradorPedidos geradorPedidos;
    private final ClienteRepository clienteRepository;
    private final GeradorMensagemWhatsapp geradorMensagemWhatsapp;

    public Page<PedidoResponseDTO> listarPedidos(Pageable pageable){
        return pedidoReposiroy.findAll(pageable)
                .map(PedidoResponseDTO::new);
    }

    public Page<PedidoResponseDTO> listarPorCliente(String nome, Pageable pageable){
        return pedidoReposiroy.findByClienteNomeContainingIgnoreCase(nome, pageable)
                .map(PedidoResponseDTO::new);
    }

    public Page<PedidoResponseDTO> listarPorTelefone(int telefone, Pageable pageable) {
        return pedidoReposiroy.findByClienteTelefone(telefone, pageable)
                .map(PedidoResponseDTO::new);
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto, String emailCliente){
        Pedido pedido = new Pedido();
        pedido.setCliente(clienteRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado")));
        pedido.setObservacoes(dto.observacoes());
        dto.itens().forEach(itemDto -> pedido.getItens().add(geradorPedidos.criarItemPedido(itemDto, pedido)));
        pedido.setValorTotal(geradorPedidos.calcularValorTotal(pedido));
        pedido.setStatus(PedidoStatus.COMPLETO);
        Pedido pedidoSalvo = pedidoReposiroy.save(pedido);
        geradorPedidos.registraMovimentoEstoque(pedidoSalvo);
        geradorMensagemWhatsapp.geradorMensagem(pedidoSalvo);
        return new PedidoResponseDTO(pedidoSalvo);
    }

    @Transactional
    public void deletarPedido(UUID id){
        Pedido pedido = pedidoReposiroy.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        pedido.getItens().forEach(item -> geradorPedidos.reverterMovimentoEstoque(pedido));
        pedidoReposiroy.delete(pedido);
    }
}

