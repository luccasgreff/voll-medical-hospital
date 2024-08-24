package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.dto.address.SaveAddressDTO;
import com.hosp.med.voll.domain.model.dto.address.SaveAddressModel;
import com.hosp.med.voll.domain.model.dto.address.SaveAddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {


    @Mapping(target = "zip_code", source = "zipCode")
    SaveAddressModel addressDtoToModel(SaveAddressDTO request);

    @Mapping(target = "zipCode", source = "zip_code")
    SaveAddressResponseDTO addressModelToResponseDTO(SaveAddressModel model);


}
