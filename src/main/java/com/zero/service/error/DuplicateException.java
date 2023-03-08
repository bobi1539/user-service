package com.zero.service.error;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message){
        super(message);
    }
}
