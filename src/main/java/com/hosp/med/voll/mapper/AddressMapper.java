package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.dto.SaveAddressDTO;
import com.hosp.med.voll.domain.model.dto.SaveAddressModel;
import com.hosp.med.voll.domain.model.dto.SaveAddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AddressMapper {


    @Mapping(target = "zip_code", source = "zipCode")
    SaveAddressModel addressDtoToModel(SaveAddressDTO request);

    @Mapping(target = "zipCode", source = "zip_code")
    SaveAddressResponseDTO addressModelToResponseDTO(SaveAddressModel model);


}
