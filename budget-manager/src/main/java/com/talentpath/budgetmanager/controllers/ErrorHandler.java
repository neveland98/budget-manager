package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = BudgetDaoException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String errorMessageOnBudgetDaoException(BudgetDaoException ex, WebRequest request) {
        return request.toString() + " an error occured: " + ex.getMessage();
    }
}
