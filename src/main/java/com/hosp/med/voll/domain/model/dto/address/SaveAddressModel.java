package com.hosp.med.voll.domain.model.dto.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAddressModel {

    private String street;
    private String district;
    private String zip_code;
    private String city;
    private String state;
    private String number;
    private String complement;


}

