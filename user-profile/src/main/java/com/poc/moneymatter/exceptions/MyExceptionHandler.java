package com.poc.moneymatter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public MoneyMatterErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {

        // build a response body out of ex, and return that
        return new MoneyMatterErrorResponse("IllegalArgumentException", ex.getMessage(),"Unprocessable Entity !",  422);

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public MoneyMatterErrorResponse noHandlerFoundException(Exception ex) {
        return new MoneyMatterErrorResponse("Exception handler not found",  ex.getMessage(),"Internal error occurerd, please contact money-matter admin !", 500);
    }
}
