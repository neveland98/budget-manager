package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.constraints.Null;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = BudgetDaoException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnBudgetDaoException(BudgetDaoException ex, WebRequest request) {
        return request.toString() + " an error occurred: " + ex.getMessage();
    }

    @ExceptionHandler(value = NullArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnNullArgumentException(NullArgumentException ex, WebRequest request) {
        return request.toString() + " an error occurred: " + ex.getMessage();
    }
    @ExceptionHandler(value = NullParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnNullArgumentException(NullParameterException ex, WebRequest request) {
        return request.toString() + " an error occurred: " + ex.getMessage();
    }
    @ExceptionHandler(value = InvalidArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnNullArgumentException(InvalidArgumentException ex, WebRequest request) {
        return request.toString() + " an error occurred: " + ex.getMessage();
    }
    @ExceptionHandler(value = InvalidUserIdException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnNullArgumentException(InvalidUserIdException ex, WebRequest request) {
        return request.toString() + " an error occurred: " + ex.getMessage();
    }
    @ExceptionHandler(value = InvalidCategoryException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnNullArgumentException(InvalidCategoryException ex, WebRequest request) {
        return request.toString() + " an error occurred: " + ex.getMessage();
    }
}
