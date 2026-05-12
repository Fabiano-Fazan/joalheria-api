package com.joalheria.api.controller;

import com.joalheria.api.dto.request.PedidoRequestDTO;
import com.joalheria.api.dto.response.PedidoResponseDTO;
import com.joalheria.api.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(
            @PageableDefault(size = 3, sort = "dataPedido", direction = Sort.Direction.DESC)
            Pageable pageable){
        return ResponseEntity.ok(pedidoService.listarPedidos(pageable));
    }

    @GetMapping("/cliente")
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidosPorCliente(
            @PageableDefault(size = 3, sort = "dataPedido", direction = Sort.Direction.DESC)
            Pageable pageable,
            Authentication authentication
    ){
        return ResponseEntity.ok(pedidoService.listarPorCliente(getAuthenticatedEmail(authentication), pageable));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(
            @Valid @RequestBody PedidoRequestDTO pedidoRequestDTO,
            Authentication authentication
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.criarPedido(pedidoRequestDTO, getAuthenticatedEmail(authentication)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable String id) {
        pedidoService.deletarPedido(java.util.UUID.fromString(id));
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
