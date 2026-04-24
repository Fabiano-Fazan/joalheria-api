package com.joalheria.api.controller;

import com.joalheria.api.dto.request.ClienteRequestDTO;
import com.joalheria.api.dto.response.ClienteResponseDTO;
import com.joalheria.api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<Page<ClienteResponseDTO>> listarClientes(Pageable pageable){
        return ResponseEntity.ok(clienteService.listarClientes(pageable));
    }

    @GetMapping("/nome")
    public ResponseEntity<Page<ClienteResponseDTO>> listarClientePorNome(@RequestParam String name, Pageable pageable){
        return ResponseEntity.ok(clienteService.listarClientesPorNome(name, pageable));
    }

    @GetMapping("/id")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@RequestParam UUID id){
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping("/telefone")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarClientePorTelefone(@RequestParam String telefone, Pageable pageable){
        return ResponseEntity.ok(clienteService.buscarClientePorTelefone(telefone, pageable));
    }

    @PostMapping()
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO){
        return ResponseEntity.ok(clienteService.cadastrarCliente(clienteRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizaCliente(@PathVariable UUID id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO){
        return ResponseEntity.ok(clienteService.atualizarCliente(id, clienteRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable UUID id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
