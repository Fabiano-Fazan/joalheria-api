package com.joalheria.api.service;

import com.joalheria.api.dto.request.ClienteRequestDTO;
import com.joalheria.api.dto.response.ClienteResponseDTO;
import com.joalheria.api.exception.RecursoNaoEncontradoException;
import com.joalheria.api.model.entity.Cliente;
import com.joalheria.api.repositoy.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Page<ClienteResponseDTO> listarClientes(Pageable pageable){
        return clienteRepository.findAll(pageable)
                .map(ClienteResponseDTO::new);
    }

    public Page<ClienteResponseDTO> listarClientesPorNome(String nome, Pageable pageable){
        return clienteRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(ClienteResponseDTO::new);
    }

    public Page<ClienteResponseDTO> buscarClientePorTelefone(int telefone, Pageable pageable){
        return clienteRepository.findByTelefone(telefone, pageable)
                .map(ClienteResponseDTO::new);
    }

    public ClienteResponseDTO buscarClientePorId(UUID id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public Cliente buscarOuCadastrarPorGoogle(String email, String nome, String googleId){
        return clienteRepository.findByEmail(email)
                .map(cliente -> {
                    if (!cliente.getGoogleId().equals(googleId)) {
                        cliente.setGoogleId(googleId);
                        return clienteRepository.save(cliente);
                    }
                    return cliente;
                })
                .orElseGet(() -> {
                    Cliente novoCliente = new Cliente();
                    novoCliente.setNome(nome);
                    novoCliente.setEmail(email);
                    novoCliente.setGoogleId(googleId);
                    return clienteRepository.save(novoCliente);
                });
    }

    @Transactional
    public ClienteResponseDTO atualizarCliente(UUID id, ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        atualizaDados(clienteRequestDTO, cliente);
        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public void deletarCliente(UUID id){
        clienteRepository.deleteById(id);
    }

    private void atualizaDados(ClienteRequestDTO dto, Cliente cliente){
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
    }
}
