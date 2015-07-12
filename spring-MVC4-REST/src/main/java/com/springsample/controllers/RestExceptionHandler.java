package com.springsample.controllers;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.springsample.exceptions.BadRequestException;
import com.springsample.exceptions.ResourceNotFoundException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleNotFoundException(final Exception exception) {
        logException(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    public void handleGeneralException(final Exception exception) {
        logException(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ BadRequestException.class, IllegalArgumentException.class })
    @ResponseBody
    public String handleBadRequestException(final Exception exception) {
        logException(exception);
        return exception.getMessage();
    }

    private void logException(final Exception exception) {
    	LOGGER.error(exception.getMessage());
    }

}