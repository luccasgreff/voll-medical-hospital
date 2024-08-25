package com.hosp.med.voll.util;

import com.hosp.med.voll.domain.model.dto.error.BeanValidationErrorDTO;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtils {

    public static ArrayList<BeanValidationErrorDTO> buildBeanValidationErrorResponse(List<FieldError> errors) {

        ArrayList<BeanValidationErrorDTO> response = new ArrayList<BeanValidationErrorDTO>();

        for (var error : errors) {
            response.add(
                    BeanValidationErrorDTO.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage()).build());
        }

        return response;
    }

}
