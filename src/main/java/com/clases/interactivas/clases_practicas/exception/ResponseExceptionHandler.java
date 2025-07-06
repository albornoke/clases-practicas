package com.clases.interactivas.clases_practicas.exception;

import com.clases.interactivas.clases_practicas.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

    // Error 204 - No Content
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

    // Error 409 - Conflict
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception409.class)
    public ResponseEntity<ApiResponse409> handlerError409(HttpServletRequest request, Exception409 ex) {
        log.warn("Error 409: {}", ex.getMessage(), ex);
        ApiResponse409 response = new ApiResponse409();
        response.setCode(HttpStatus.CONFLICT.value());
        response.setMessage("El recurso está duplicado");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Error 424 - Failed Dependency
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
        StringBuilder sb = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            sb.append("Campo '")
            .append(error.getField())
            .append("': ")
            .append(error.getDefaultMessage())
            .append("\n");
        }
        response.setMessage(sb.toString().trim());
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }


}
