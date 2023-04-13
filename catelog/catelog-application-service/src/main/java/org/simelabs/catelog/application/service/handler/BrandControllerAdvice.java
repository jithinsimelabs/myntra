package org.simelabs.catelog.application.service.handler;

import org.simelabs.catelog.application.service.exception.BrandException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BrandControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BrandException.class})
    public ResponseEntity<?> handleBrandException(BrandException ex, WebRequest request){
        return new ResponseEntity<>(
                new CatelogErrorMessage(ex.getMessage()),
                ex.getStatus()
        );
    }

}
