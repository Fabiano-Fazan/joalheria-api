package com.joalheria.api.repositoy;

import com.joalheria.api.model.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {
}
