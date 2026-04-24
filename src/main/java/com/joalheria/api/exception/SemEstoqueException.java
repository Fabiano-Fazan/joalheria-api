package com.joalheria.api.exception;

public class SemEstoqueException extends RuntimeException{
    public SemEstoqueException(String message) {
        super(message);
    }
}
