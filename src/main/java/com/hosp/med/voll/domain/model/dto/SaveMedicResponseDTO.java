package com.hosp.med.voll.domain.model.dto;

import com.hosp.med.voll.domain.enums.SpecialtysEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SaveMedicResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String crm;

    private SpecialtysEnum specialty;

    private SaveAddressResponseDTO address;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
