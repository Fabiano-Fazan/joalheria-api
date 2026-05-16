package com.joalheria.api.event;

import java.util.List;

public record ProdutoImagemRollbackEvent(
        List<String> publicIds
) {
}
