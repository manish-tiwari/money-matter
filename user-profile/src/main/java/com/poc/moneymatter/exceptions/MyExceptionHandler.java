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
    public MoneyMatterErrorResponse handleMoneyMatterUserException(MoneyMatterUserException ex) {

        // build a response body out of ex, and return that
        return new MoneyMatterErrorResponse("MoneyMatterUserException", "Unprocessable Entity.", ex.getMessage(),  422,new Date());

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public MoneyMatterErrorResponse noHandlerFoundException(Exception ex) {
        return new MoneyMatterErrorResponse("Exception handler not found",  ex.getMessage(),"Internal error occurerd, please contact money-matter admin.", 500, new Date());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public MoneyMatterErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return new MoneyMatterErrorResponse("UserNotFoundException", "Entity not found.", ex.getMessage(), 404, new Date());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MoneyMatterErrorResponse handleUserExistsException(UserAlreadyExistsException ex) {
        return new MoneyMatterErrorResponse("UserAlreadyExistsException", "Constraint Voilation", ex.getMessage(), 400, new Date());
    }
}
