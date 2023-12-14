package com.gira.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler implements
        MessageSourceAware,
        AuthenticationEntryPoint,
        AccessDeniedHandler {
    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessage(String code, Object...args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,HttpStatusCode status,
                                                                  WebRequest request)
                {
        var message = getMessage("MethodArgumentNotValidException.message");
        var errors = new HashMap<String, String>();
                    for (FieldError error : exception.getFieldErrors()) {
                        String key = error.getField();
                        String value = error.getDefaultMessage();
                        errors.put(key, value);
                    }
                    var response = new ErrorResponse(message, errors);
                    return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException exception
    ) {
        var message = getMessage("ConstraintViolationException.message");
        var errors = new HashMap<String, String>();
        for (var error : exception.getConstraintViolations()) {
            var key = error.getPropertyPath().toString();
            var value = error.getMessage();
            errors.put(key, value);
        }
        var response = new ErrorResponse(message, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        var message = getMessage("AuthenticationException.message");
        var error = new ErrorResponse(message);
        var out = response.getOutputStream();
        new ObjectMapper().writeValue(out, error);

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        var message = getMessage("AccessDeniedException.message");
        var error = new ErrorResponse(message);
        var out = response.getOutputStream();
        new ObjectMapper().writeValue(out, error);
    }
}
