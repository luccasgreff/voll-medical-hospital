package com.hosp.med.voll.handler;

import com.hosp.med.voll.domain.model.dto.error.BeanValidationErrorDTO;
import com.hosp.med.voll.domain.model.dto.error.ErrorDTO;
import com.hosp.med.voll.domain.model.exception.UnactiveException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleInvalidLogin() {

        ErrorDTO error = new ErrorDTO();
        error.setMessage("Unable to generate token, login or password provided are invalid.");

        log.error("Token won't be generated, because login or password are invalid.");

        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFound() {

        log.error("The requested content can't be found.");
        return ResponseEntity.notFound().build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBadRequest(MethodArgumentNotValidException exception) {

        var errors = exception.getFieldErrors();
        ArrayList<BeanValidationErrorDTO> response = new ArrayList<BeanValidationErrorDTO>();

        StringBuilder fieldErrorsForLog = new StringBuilder();
        fieldErrorsForLog.append("{");

        for (var error : errors) {

            response.add(BeanValidationErrorDTO.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage()).build());

            fieldErrorsForLog.append(error.getField());

            if (errors.size() != response.size()) {
                fieldErrorsForLog.append(", ");
            } else {
                fieldErrorsForLog.append("}");
            }
        }

        log.error("Bad Request due to: {}", fieldErrorsForLog);
        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        ErrorDTO error = new ErrorDTO();
        error.setMessage("The request content violates data integrity, such as unique data or data size.");

        log.error("Data integrity violated: {Message: {}}", exception.getMessage());

        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnactiveException.class)
    public ResponseEntity handleUnactiveException(UnactiveException exception) {

        ErrorDTO error = new ErrorDTO();
        error.setMessage(exception.getMessage());

        log.error("Unactive records can't be updated.");

        return ResponseEntity.badRequest().body(error);
    }

}
