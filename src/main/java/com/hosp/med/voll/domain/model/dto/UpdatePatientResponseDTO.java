package com.hosp.med.voll.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientResponseDTO {

    private Integer id;

    private String name;

    private String phone;

    private SaveAddressResponseDTO address;

}
