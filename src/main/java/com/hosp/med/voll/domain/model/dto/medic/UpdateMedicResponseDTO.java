package com.hosp.med.voll.domain.model.dto.medic;

import com.hosp.med.voll.domain.model.dto.address.SaveAddressResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMedicResponseDTO {

    private Integer id;

    private String name;

    private String phone;

    private SaveAddressResponseDTO address;

}
