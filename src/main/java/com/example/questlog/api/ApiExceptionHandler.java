package com.example.questlog.api;

import com.example.questlog.service.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Not found");
        pd.setType(URI.create("https://example.com/problems/not-found"));
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        pd.setTitle("Validation error");
        pd.setType(URI.create("https://example.com/problems/validation"));
        pd.setProperty("path", req.getRequestURI());

        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail malformed(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Malformed JSON request");
        pd.setTitle("Bad request");
        pd.setType(URI.create("https://example.com/problems/malformed-json"));
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ProblemDetail spring(ErrorResponseException ex, HttpServletRequest req) {
        ProblemDetail pd = ex.getBody();
        if (pd != null) pd.setProperty("path", req.getRequestURI());
        return pd;
    }
}
