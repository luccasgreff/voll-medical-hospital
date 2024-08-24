package com.hosp.med.voll.domain.model.dto.patient;

import com.hosp.med.voll.domain.model.dto.address.SaveAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePatientDTO {

    @NotBlank(message = "{field.blank}")
    private String name;

    @NotBlank(message = "{field.blank}")
    @Email(message = "{field.email}")
    private String email;

    @NotBlank(message = "{field.blank}")
    @Pattern(regexp = "(\\d{11})|(\\d{10})", message = "{field.pattern}")
    private String phone;

    @NotBlank(message = "{field.blank}")
    @Pattern(regexp = "([0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}[/]?[0-9]{2})|([0-9]{3}[-]?[0-9]{2}[-]?[0-9]{4})", message = "{field.pattern}")
    private String ssn;

    @NotNull(message = "{field.null}")
    @Valid
    private SaveAddressDTO address;

}
