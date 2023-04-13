package org.simelabs.catelog.application.service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BrandException extends RuntimeException {
    private String message = "";
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public BrandException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BrandException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

}
