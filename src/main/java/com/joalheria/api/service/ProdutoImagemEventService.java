package com.joalheria.api.service;

import com.joalheria.api.event.ProdutoImagemRollbackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProdutoImagemEventService {

    private final CloudinaryService cloudinaryService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleProdutoImagemRollback(ProdutoImagemRollbackEvent event) {
        log.info("Iniciando compensação: removendo imagens do Cloudinary após rollback da transação.");
        event.publicIds().forEach(publicId -> {
            try {
                cloudinaryService.deletarImagem(publicId);
            } catch (Exception e) {
                log.error("Erro ao remover imagem {} do Cloudinary durante compensação: {}", publicId, e.getMessage());
            }
        });
    }
}
