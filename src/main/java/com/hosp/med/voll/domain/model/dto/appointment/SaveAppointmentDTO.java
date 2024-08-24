package com.hosp.med.voll.domain.model.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAppointmentDTO {


    @NotBlank(message = "{field.blank}")
    private String patient;

    @NotBlank(message = "{field.blank}")
    private String medic;

    @Pattern(regexp = "\\d{2}[/]?\\d{2}[/]?\\d{4}", message = "{field.pattern}")
    @NotBlank(message = "{field.blank}")
    private String date;

    @Pattern(regexp = "\\d{2}[:]?\\d{2}", message = "{field.pattern}")
    @NotBlank(message = "{field.blank}")
    private String time;


}
