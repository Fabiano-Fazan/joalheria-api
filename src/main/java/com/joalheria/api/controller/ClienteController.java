package com.joalheria.api.controller;

import com.joalheria.api.dto.request.ClienteRequestDTO;
import com.joalheria.api.dto.response.ClienteResponseDTO;
import com.joalheria.api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @PutMapping("/{email}")
    public ResponseEntity<ClienteResponseDTO> atualizaCliente(
            @PathVariable String email,
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO,
            Authentication authentication
    ){
        return ResponseEntity.ok(clienteService.atualizarCliente(
                email,
                clienteRequestDTO,
                getAuthenticatedEmail(authentication),
                authentication.getAuthorities()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable UUID id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    private String getAuthenticatedEmail(Authentication authentication) {
        var principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User user) {
            return user.getAttribute("email");
        }

        if (principal instanceof Jwt jwt) {
            return jwt.getClaimAsString("email");
        }

        return authentication.getName();
    }
}
