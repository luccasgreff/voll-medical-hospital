package com.hosp.med.voll.mapper.impl;

import com.hosp.med.voll.domain.model.dto.SaveAddressDTO;
import com.hosp.med.voll.domain.model.dto.SaveAddressModel;
import com.hosp.med.voll.domain.model.dto.SaveAddressResponseDTO;
import com.hosp.med.voll.mapper.AddressMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public SaveAddressModel addressDtoToModel(SaveAddressDTO body) {
        var model = new SaveAddressModel();
        model.setStreet(body.getStreet());
        model.setDistrict(body.getDistrict());
        model.setZip_code(body.getZipCode());
        model.setCity(body.getCity());
        model.setState(body.getState());
        model.setNumber(body.getNumber());
        model.setComplement(body.getComplement());
        return model;
    }
    @Override
    public SaveAddressResponseDTO addressModelToResponseDTO(SaveAddressModel model) {
        SaveAddressResponseDTO responseDTO = new SaveAddressResponseDTO();
        responseDTO.setStreet(model.getStreet());
        responseDTO.setDistrict(model.getDistrict());
        responseDTO.setZipCode(model.getZip_code());
        responseDTO.setCity(model.getCity());
        responseDTO.setState(model.getState());
        responseDTO.setNumber(model.getNumber());
        responseDTO.setComplement(model.getNumber());
        return responseDTO;
    }

}
