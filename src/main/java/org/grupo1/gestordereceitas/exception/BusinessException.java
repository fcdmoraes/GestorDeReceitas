package org.grupo1.gestordereceitas.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String mensage){
        super(mensage);
    }
}
