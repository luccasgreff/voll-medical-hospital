package com.hosp.med.voll.domain.model.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAppointmentDTO {

    private Integer id;
    private String patient;
    private String medic;
    private String date;
    private String time;

}
