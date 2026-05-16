package com.joalheria.api.dto.request;

import java.time.LocalDateTime;

public record DashBoardRequestDTO(
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {
}
