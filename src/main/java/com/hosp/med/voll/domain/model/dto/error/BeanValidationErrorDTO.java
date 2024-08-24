package com.hosp.med.voll.domain.model.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeanValidationErrorDTO {

    private String field;

    private String message;

}
