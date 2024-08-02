package com.hosp.med.voll.domain.model.dto;

import com.hosp.med.voll.domain.enums.SpecialtysEnum;
import com.hosp.med.voll.domain.model.MedicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMedicDTO {

    private Integer id;
    private String name;
    private String email;
    private String crm;
    private SpecialtysEnum specialty;

}
