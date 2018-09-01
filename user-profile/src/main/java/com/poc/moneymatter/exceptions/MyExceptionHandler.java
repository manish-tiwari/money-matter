package com.poc.moneymatter.exceptions;

import com.poc.moneymatter.exceptions.models.MoneyMatterErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(MoneyMatterUserException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public MoneyMatterErrorResponse handleIllegalArgumentException(MoneyMatterUserException ex) {

        // build a response body out of ex, and return that
        return new MoneyMatterErrorResponse("MoneyMatterUserException", "Unprocessable Entity.", ex.getMessage(),  422,new Date());

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public MoneyMatterErrorResponse noHandlerFoundException(Exception ex) {
        return new MoneyMatterErrorResponse("Exception handler not found",  ex.getMessage(),"Internal error occurerd, please contact money-matter admin.", 500, new Date());
    }
}
