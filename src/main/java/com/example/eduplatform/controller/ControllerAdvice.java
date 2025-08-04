package com.example.eduplatform.controller;

import com.example.eduplatform.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(
            final ResourceNotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleUserAlreadyExists(
            final UserAlreadyExistsException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(DuplicateModuleIndexException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleDuplicateModuleIndex(
            final DuplicateModuleIndexException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(AlreadyEnrolledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAlreadyEnrolled(
            final AlreadyEnrolledException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access denied.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e
    ) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingMessage, newMessage) ->
                                existingMessage + " " + newMessage)
                ));
        return exceptionBody;
    }

}
