package com.joalheria.api.repositoy;

import com.joalheria.api.model.entity.Produtos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRespository extends JpaRepository<Produtos, UUID> {
    Page<Produtos> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Produtos> findByCategoriaContainingIgnoreCase(String categoria, Pageable pageable);

}
