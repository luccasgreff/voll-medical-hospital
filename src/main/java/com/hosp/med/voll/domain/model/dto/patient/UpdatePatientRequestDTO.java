package com.hosp.med.voll.domain.model.dto.patient;

import com.hosp.med.voll.domain.model.dto.address.SaveAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientRequestDTO {

    @NotNull(message = "{field.null}")
    private Integer id;

    private String name;

    private String phone;

    @Valid
    private SaveAddressDTO address;

}
