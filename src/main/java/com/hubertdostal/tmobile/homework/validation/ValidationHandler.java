package com.hubertdostal.tmobile.homework.validation;

import com.hubertdostal.tmobile.homework.exception.TaskNotFoundException;
import com.hubertdostal.tmobile.homework.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom validation and error handler configuration for controller layer
 *
 * @author hubert.dostal@gmail.com
 */
@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for {@link UserNotFoundException}
     *
     * @param ex      runtime exception to be handled
     * @param request instance of web request
     * @return instance of ResponseEntity<Object> to places in response
     */
    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException(RuntimeException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        String message = "User not found in database for ID " + ((UserNotFoundException) ex.getCause()).getNotFoundId();
        String fieldName = ((UserNotFoundException) ex.getCause()).getFieldName();
        errors.put(fieldName, message);
        return handleExceptionInternal(ex, errors,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Exception handler for {@link TaskNotFoundException}
     *
     * @param ex      runtime exception to be handled
     * @param request instance of web request
     * @return instance of ResponseEntity<Object> to places in response
     */
    @ExceptionHandler(value = {TaskNotFoundException.class})
    protected ResponseEntity<Object> handleTaskNotFoundException(RuntimeException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        String message = "Task not found in database for ID " + ((TaskNotFoundException) ex.getCause()).getNotFoundId();
        String fieldName = "id";
        errors.put(fieldName, message);
        return handleExceptionInternal(ex, errors,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
