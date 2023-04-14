package org.simelabs.catelog.application.service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class BrandNotFoundException extends BaseNotFoundException {
    public BrandNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
