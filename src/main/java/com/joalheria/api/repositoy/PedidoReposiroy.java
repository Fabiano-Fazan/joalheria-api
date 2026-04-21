package com.joalheria.api.repositoy;

import com.joalheria.api.model.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoReposiroy extends JpaRepository<Pedido, UUID> {
    Page<Pedido> findByClienteNomeContainingIgnoreCase(String nome, Pageable pageable);
}
