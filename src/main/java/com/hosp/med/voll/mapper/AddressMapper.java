package com.hosp.med.voll.mapper;

import com.hosp.med.voll.domain.model.dto.SaveAddressDTO;
import com.hosp.med.voll.domain.model.dto.SaveAddressModel;
import com.hosp.med.voll.domain.model.dto.SaveAddressResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface AddressMapper {

    SaveAddressModel addressDtoToModel(SaveAddressDTO request);

    SaveAddressResponseDTO addressModelToResponseDTO(SaveAddressModel model);


}
