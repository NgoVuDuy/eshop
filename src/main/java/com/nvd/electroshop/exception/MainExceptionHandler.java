package com.nvd.electroshop.exception;

import com.nvd.electroshop.dto.response.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Message exceptionHandler(Exception e, WebRequest w) {

        return new Message(0, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message badRequestException(Exception e, WebRequest w) {

        return new Message(0, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Message resourceNotFoundException(Exception e, WebRequest w) {

        return new Message(0, e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Message conflictException(Exception e, WebRequest w) {

        return new Message(0, e.getMessage());
    }
}
