package com.joalheria.api.repositoy;

import com.joalheria.api.model.entity.Produtos;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRespository extends JpaRepository<Produtos, UUID> {
    Page<Produtos> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Produtos> findByCategoriaContainingIgnoreCase(String categoria, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Produtos> findWithLockById(UUID id);
}
