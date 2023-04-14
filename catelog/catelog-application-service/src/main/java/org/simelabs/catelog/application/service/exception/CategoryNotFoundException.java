package org.simelabs.catelog.application.service.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BaseNotFoundException {
    public CategoryNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
