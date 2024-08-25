package com.hosp.med.voll.domain.model.dto.medic;

import com.hosp.med.voll.domain.enums.SpecialtysEnum;
import com.hosp.med.voll.domain.model.dto.address.SaveAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveMedicDTO {

    @NotBlank(message = "{field.blank}")
    private String name;

    @NotBlank(message = "{field.blank}")
    @Email(message = "{field.email}")
    private String email;

    @NotBlank(message = "{field.blank}")
    @Pattern(regexp = "(\\d{11})|(\\d{10})", message = "{field.pattern}")
    private String phone;

    @NotBlank(message = "{field.blank}")
    @Size(max = 6, message = "{field.size}")
    private String crm;

    @NotNull(message = "{field.null}")
    private SpecialtysEnum specialty;

    @NotNull(message = "{field.null}")
    @Valid
    private SaveAddressDTO address;

}
