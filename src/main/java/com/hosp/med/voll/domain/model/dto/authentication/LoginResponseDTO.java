package com.hosp.med.voll.domain.model.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private Integer id;

    private String login;

    private String password;

}
