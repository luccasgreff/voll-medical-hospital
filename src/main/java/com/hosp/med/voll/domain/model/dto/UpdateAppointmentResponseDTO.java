package com.hosp.med.voll.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentResponseDTO {

    private Integer id;

    private String date;

    private String time;

}
