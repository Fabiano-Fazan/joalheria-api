package com.joalheria.api.dto.response;

import java.time.LocalDateTime;

public record DashboardResponseDTO(
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Long totalPedidos
) {
}
