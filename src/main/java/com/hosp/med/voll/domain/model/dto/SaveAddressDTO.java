package com.hosp.med.voll.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAddressDTO {

    @NotNull(message = "{field.null}")
    @NotBlank(message = "{field.blank}")
    private String street;

    @NotNull(message = "{field.null}")
    @NotBlank(message = "{field.blank}")
    private String district;

    @NotBlank(message = "{field.blank}")
    @Pattern(regexp = "([0-9]{5}[-]?[0-9]{3})|([0-9]{5}[-]?[0-9]{4})|(\\d{5})", message = "{field.pattern}")
    private String zipCode;

    @NotNull(message = "{field.null}")
    @NotBlank(message = "{field.blank}")
    private String city;

    @NotBlank(message = "{field.blank}")
    @Size(max = 15, message = "{field.size}")
    private String state;

    @NotBlank(message = "{field.blank}")
    @Size(max = 3, min = 1, message = "{field.size}")
    private String number;

    private String complement;

}
