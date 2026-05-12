package com.joalheria.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    private ResponseEntity<ErroPadrao> geraErro(String menssagem, HttpStatus status, String erro, String path){
        ErroPadrao erroPadrao = new ErroPadrao();
        erroPadrao.setMensagem(menssagem);
        erroPadrao.setStatus(status.value());
        erroPadrao.setErro(erro);
        erroPadrao.setPath(path);
        erroPadrao.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(status).body(erroPadrao);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErroPadrao> negocio(NegocioException e, HttpServletRequest request){
        return geraErro(e.getMessage(), HttpStatus.BAD_REQUEST, "Regra de negócio inválida", request.getRequestURI());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> recursoNaoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest request){
        return geraErro(e.getMessage(), HttpStatus.NOT_FOUND, "Recurso não encontrado", request.getRequestURI());
    }

    @ExceptionHandler(SemEstoqueException.class)
    public ResponseEntity<ErroPadrao> semEstoque(SemEstoqueException e, HttpServletRequest request){
        return geraErro(e.getMessage(), HttpStatus.BAD_REQUEST, "Sem estoque", request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadrao> illegalArgument(IllegalArgumentException e, HttpServletRequest request){
        return geraErro(e.getMessage(), HttpStatus.BAD_REQUEST, "Parâmetro inválido", request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        String mensagem = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return geraErro(mensagem, HttpStatus.BAD_REQUEST, "Dados inválidos", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> erroInterno(Exception e, HttpServletRequest request){
        return geraErro("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", request.getRequestURI());
    }
}
