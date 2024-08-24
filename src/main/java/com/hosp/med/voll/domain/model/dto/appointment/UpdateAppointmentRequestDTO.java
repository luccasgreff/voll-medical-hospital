package com.hosp.med.voll.domain.model.dto.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentRequestDTO {

    @NotNull(message = "{field.null}")
    private Integer id;

    private String date;

    private String time;

}
