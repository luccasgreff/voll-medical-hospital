package com.hosp.med.voll.handler;

import com.hosp.med.voll.domain.model.dto.BeanValidationErrorDTO;
import com.hosp.med.voll.domain.model.dto.ErrorDTO;
import com.hosp.med.voll.handler.exception.UnactiveException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleInvalidLogin() {
        ErrorDTO error = new ErrorDTO();
        error.setMessage("Unable to generate token, login or password provided are invalid.");
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFound() {
        return ResponseEntity.notFound().build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBadRequest(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();
        ArrayList<BeanValidationErrorDTO> response = new ArrayList<BeanValidationErrorDTO>();

        for (var error : errors) {
            response.add(BeanValidationErrorDTO.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage()).build());
        }

        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleUniqueIndexData(DataIntegrityViolationException exception) {

        ErrorDTO error = new ErrorDTO();
        error.setMessage("Field(s) content already exists");
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnactiveException.class)
    public ResponseEntity handleUnactiveException(UnactiveException exception) {

        ErrorDTO error = new ErrorDTO();

        error.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

}
