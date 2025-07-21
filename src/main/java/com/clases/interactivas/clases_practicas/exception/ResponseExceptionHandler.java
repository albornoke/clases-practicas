package com.clases.interactivas.clases_practicas.exception;

import com.clases.interactivas.clases_practicas.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest httpServletRequest;

    // Error 204 - No contenido
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception204.class)
    public ResponseEntity<ApiResponse204> handlerError204(HttpServletRequest request, Exception204 ex) {
        log.warn("Error 204: {}", ex.getMessage(), ex);
        ApiResponse204 response = new ApiResponse204();
        response.setCode(HttpStatus.NO_CONTENT.value());
        response.setMessage("No se encontró contenido");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    // Error 400 - Bad Request
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception400.class)
    public ResponseEntity<ApiResponse400> handlerError400(HttpServletRequest request, Exception400 ex) {
        log.warn("Error 400: {}", ex.getMessage(), ex);
        ApiResponse400 response = new ApiResponse400();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Los datos ingresados son inválidos");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Error 409 - Conflicto
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception409.class)
    public ResponseEntity<ApiResponse409> handlerError409(HttpServletRequest request, Exception409 ex) {
        log.warn("Error 409: {}", ex.getMessage(), ex);
        ApiResponse409 response = new ApiResponse409();
        response.setCode(HttpStatus.CONFLICT.value());
        response.setMessage("El recurso está duplicado");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Error 424 - Fallo dependencia
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception424.class)
    public ResponseEntity<ApiResponse424> handlerError424(HttpServletRequest request, Exception424 ex) {
        log.error("Error 424: {}", ex.getMessage(), ex);
        ApiResponse424 response = new ApiResponse424();
        response.setCode(HttpStatus.FAILED_DEPENDENCY.value());
        response.setMessage("Ocurrió un error inesperado. Por favor comuníquese con el área de soporte.");
        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }

    // Validaciones con @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ApiResponse400 response = new ApiResponse400();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> errorObj = new HashMap<>();
            errorObj.put("field", error.getField());
            errorObj.put("message", error.getDefaultMessage());
            errors.add(errorObj);
        }        
        Map<String, Object> body = new HashMap<>();
        body.put("code", response.getCode());
        body.put("errors", errors);
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            Map<String, String> errorObj = new HashMap<>();
            // Extrae el nombre del campo (última parte del path)
            String field = violation.getPropertyPath().toString();
            if (field.contains(".")) {
                field = field.substring(field.lastIndexOf('.') + 1);
            }
            errorObj.put("field", field);
            errorObj.put("message", violation.getMessage());
            errors.add(errorObj);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
