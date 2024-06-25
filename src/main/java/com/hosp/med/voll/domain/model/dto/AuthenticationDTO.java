package com.hosp.med.voll.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {

    @NotBlank(message = "{field.blank}")
    private String login;

    @NotBlank(message = "{field.blank}")
    @Size(min = 3, max = 12, message = "{field.size}")
    private String password;

}
