package com.joalheria.api.repositoy;

import com.joalheria.api.model.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Boolean existsClienteByEmailContainingIgnoreCase(String email);
    Page<Cliente> findByTelefone(String telefone,Pageable pageable);

}
