package com.article.api.exception;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException(String message) {
        super(message);
    }
}