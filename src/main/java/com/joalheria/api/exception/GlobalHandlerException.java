package com.joalheria.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    private ResponseEntity<ErroPadrao> geraErro(String menssagem, HttpStatus status, String erro, String path){
        ErroPadrao erroPadrao = new ErroPadrao();
        erroPadrao.setMensagem(menssagem);
        erroPadrao.setStatus(status.value());
        erroPadrao.setErro(erro);
        erroPadrao.setPath(path);
        return ResponseEntity.status(status).body(erroPadrao);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> recursoNaoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest request){
        return geraErro(e.getMessage(), HttpStatus.NOT_FOUND, "Recurso não encontrado", request.getRequestURI());
    }

    @ExceptionHandler(SemEstoqueException.class)
    public ResponseEntity<ErroPadrao> semEstoque(SemEstoqueException e, HttpServletRequest request){
        return geraErro(e.getMessage(), HttpStatus.BAD_REQUEST, "Sem estoque", request.getRequestURI());
    }
}
