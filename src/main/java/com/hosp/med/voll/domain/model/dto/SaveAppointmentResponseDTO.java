package com.hosp.med.voll.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAppointmentResponseDTO {

    private Integer id;

    private String patient;

    private String medic;

    private String date;

    private String time;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;



}
