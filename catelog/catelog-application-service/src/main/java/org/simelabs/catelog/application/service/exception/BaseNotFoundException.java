package org.simelabs.catelog.application.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseNotFoundException extends RuntimeException {

    private String errorMessage = "";
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public BaseNotFoundException(String errorMessage, HttpStatus status) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.status = status;
    }

}
