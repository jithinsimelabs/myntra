package org.simelabs.catelog.application.service.handler;

import org.simelabs.catelog.application.service.exception.BaseNotFoundException;
import org.simelabs.catelog.application.service.exception.BrandNotFoundException;
import org.simelabs.catelog.application.service.exception.CategoryNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CatelogControllerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BrandNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<?> handleBrandException(BaseNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(
                new CatelogErrorMessage(ex.getErrorMessage()),
                ex.getStatus()
        );
    }

}
