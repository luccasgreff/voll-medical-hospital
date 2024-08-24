package com.hosp.med.voll.domain.model.dto.patient;

import com.hosp.med.voll.domain.model.dto.address.SaveAddressResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePatientResponseDTO {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String ssn;

    private SaveAddressResponseDTO address;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
